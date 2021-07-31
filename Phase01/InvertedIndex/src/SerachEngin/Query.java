package SerachEngin;

import java.util.List;
import java.util.Set;

public class Query {
    private Set<String> mustBeDocsList ;
    private Set<String> mustNotBeDocsList ;
    private Set<String> shouldBeDocsList ;

    public Query(Set<String> mustBeDocsList, Set<String> mustNotBeDocsList, Set<String> shouldBeDocsList) {
        this.mustBeDocsList = mustBeDocsList;
        this.mustNotBeDocsList = mustNotBeDocsList;
        this.shouldBeDocsList = shouldBeDocsList;
    }

    public Set<String> getMustBeDocsList() {
        return mustBeDocsList;
    }

    public void setMustBeDocsList(Set<String> mustBeDocsList) {
        this.mustBeDocsList = mustBeDocsList;
    }

    public Set<String> getMustNotBeDocsList() {
        return mustNotBeDocsList;
    }

    public void setMustNotBeDocsList(Set<String> mustNotBeDocsList) {
        this.mustNotBeDocsList = mustNotBeDocsList;
    }

    public Set<String> getShouldBeDocsList() {
        return shouldBeDocsList;
    }

    public void setShouldBeDocsList(Set<String> shouldBeDocsList) {
        this.shouldBeDocsList = shouldBeDocsList;
    }
}
