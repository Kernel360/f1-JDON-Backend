package kernel.jdon.modulebatch.job.course.reader.converter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SkillStandardizer {

    private static final Map<String, String> skillAliases = new HashMap<>();

    static {
        skillAliases.put("Spring Boot", "springboot");
        skillAliases.put("머신러닝", "ml");
        skillAliases.put("Java Persistence API", "jpa");
    }

    public static String standardize(String skill) {
        if (skill.contains("/")) {
            return Arrays.stream(skill.split("/"))
                .map(SkillStandardizer::normalizeAndLookup)
                .collect(Collectors.joining(", "));
        } else {
            return normalizeAndLookup(skill);
        }
    }

    private static String normalizeAndLookup(String skill) {
        String normalizedSkill = skill.toLowerCase().replaceAll("\\s+", "");

        return skillAliases.getOrDefault(normalizedSkill, skill);
    }
}
