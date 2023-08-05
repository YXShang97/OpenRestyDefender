package org.example;

import java.util.List;

public class AndRule implements Rule {
    private List<Rule> rules;

    public AndRule(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public boolean check(HttpRequest httpRequest) {
        for (Rule rule : rules) {
            if (!rule.check(httpRequest)) {
                return false;
            }
        }
        return true;
    }
}
