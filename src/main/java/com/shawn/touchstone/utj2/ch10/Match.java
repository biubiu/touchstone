package com.shawn.touchstone.utj2.ch10;

public class Match {
    public final String searchString;
    public final String surroundingContext;
    public final String searchTitle;
    
    public Match(String searchTitle, String searchString, String surroundingContext) {
        this.searchTitle = searchTitle;
        this.searchString = searchString;
        this.surroundingContext = surroundingContext;
    }

    @Override
    public String toString() {
        return searchString + " [" + surroundingContext + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((searchString == null) ? 0 : searchString.hashCode());
        result = prime * result + ((searchTitle == null) ? 0 : searchTitle.hashCode());
        result = prime * result + ((surroundingContext == null) ? 0 : surroundingContext.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Match other = (Match) obj;
        if (searchString == null) {
            if (other.searchString != null)
                return false;
        } else if (!searchString.equals(other.searchString))
            return false;
        if (searchTitle == null) {
            if (other.searchTitle != null)
                return false;
        } else if (!searchTitle.equals(other.searchTitle))
            return false;
        if (surroundingContext == null) {
            if (other.surroundingContext != null)
                return false;
        } else if (!surroundingContext.equals(other.surroundingContext))
            return false;
        return true;
    }
}