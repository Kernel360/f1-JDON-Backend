package kernel.jdon.crawler.inflearn.converter;

public enum KeywordTranslator {
	JAVA("java", "자바"),
	SPRING("spring", "스프링"),
	PYTHON("python", "파이썬"),
	JAVASCRIPT("javascript", "자바스크립트"),
	SPRING_BOOT("spring boot", "스프링 부트"),
	REACT("react", "리액트");
	// TODO: 번역 작업 필요

	private final String englishKeyword;
	private final String koreanTranslation;

	KeywordTranslator(String englishKeyword, String koreanTranslation) {
		this.englishKeyword = englishKeyword;
		this.koreanTranslation = koreanTranslation;
	}

	public String getEnglishKeyword() {
		return englishKeyword;
	}

	public String getKoreanTranslation() {
		return koreanTranslation;
	}

	public static String translateToKorean(String keyword) {
		for (KeywordTranslator translation : KeywordTranslator.values()) {
			if (translation.getEnglishKeyword().equalsIgnoreCase(keyword)) {
				return translation.getKoreanTranslation();
			}
		}
		return keyword;
	}
}
