package com.shawn.touchstone.utj2.ch12;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ProfileTest {

    private Profile profile;

    private Criteria criteria;

    private Question questionIsThereRelocation;

    private Question  questionIsThereReimburse;

    private Answer answerThereIsRelocation;

    private Answer answerThereIsNotRelocation;

    private Answer answerDoesNotReimburseTuition;

    private Answer answerReimburseTuition;

    @Before
    public void createProfile() {
        profile = new Profile();
        criteria = new Criteria();
    }

    @Before
    public void createQuestionAndAnswer() {
        questionIsThereRelocation = new BooleanQuestion(1, "Relocation ?");
        questionIsThereReimburse = new BooleanQuestion(2, "reimburse tuition ?");

        answerThereIsRelocation = new Answer(questionIsThereRelocation, Bool.True);
        answerThereIsNotRelocation = new Answer(questionIsThereRelocation, Bool.FALSE);
        answerDoesNotReimburseTuition = new Answer(questionIsThereReimburse, Bool.FALSE);
        answerReimburseTuition = new Answer(questionIsThereReimburse, Bool.True);
    }

    @Test
    public void matchesNothingWhenProfileEmpty(){
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.DontCare);
        criteria.add(criterion);

        boolean result = profile.matches(criteria);

        assertFalse(result);
    }

    @Test
    public void matchesWhenProfileContainsMatchingAnswer() {
        profile.add(answerThereIsRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);
        criteria.add(criterion);

        boolean result = profile.matches(criteria);

        assertTrue(result);
    }

    @Test
    public void doesNotMatchWhenNoMatchinAnswer() {
        profile.add(answerThereIsNotRelocation);
        Criterion criterion = new Criterion(answerThereIsRelocation, Weight.Important);
        criteria.add(criterion);

        boolean result = profile.matches(criteria);

        assertFalse(result);
    }

    @Test
    public void matchesWhenContainsMultipleAnswers() {
        profile.add(answerThereIsRelocation);
        profile.add(answerDoesNotReimburseTuition);

        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));

        boolean result = profile.matches(criteria);

        assertTrue(result);
    }

    @Test
    public void matchAgainstNullAnswerReturnsFalse() {
        assertFalse(new Answer(new BooleanQuestion(0, ""), Bool.True).match(null));
    }

    @Test
    public void doesNotMatchWhenNoneOfMultipleCriteriaMatch() {
        profile.add(answerDoesNotReimburseTuition);
        Criteria criteria = new Criteria();
        criteria.add(new Criterion(answerThereIsRelocation, Weight.Important));
        criteria.add(new Criterion(answerReimburseTuition, Weight.Important));

        boolean result = profile.matches(criteria);

        assertFalse(result);
    }

    @Test
    public void doesNotMatchWhenAnyMustMeetCriteriaNotMet() {
        profile.add(answerThereIsNotRelocation);
        profile.add(answerDoesNotReimburseTuition);

        criteria.add(new Criterion(answerThereIsRelocation, Weight.MustMatch));
        criteria.add(new Criterion(answerReimburseTuition, Weight.Important));

        assertFalse(profile.matches(criteria));
    }

    @Test
    public void matchesWhenCriterionIsDontCare() {
        profile.add(answerDoesNotReimburseTuition);
        Criterion criterion = new Criterion(answerReimburseTuition, Weight.DontCare);
        criteria.add(criterion);
        assertTrue(profile.matches(criteria));
    }
}
