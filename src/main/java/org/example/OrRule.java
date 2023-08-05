package org.example;

import java.util.List;

public class OrRule implements Rule {
    private List<Rule> rules;

    public OrRule(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean check(HttpRequest httpRequest) {
        for (Rule rule : rules) {
            if (rule.check(httpRequest)) {
                return true;
            }
        }
        return false;
    }
}
