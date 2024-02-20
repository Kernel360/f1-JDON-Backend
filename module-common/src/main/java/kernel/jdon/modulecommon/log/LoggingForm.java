package kernel.jdon.modulecommon.log;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class LoggingForm {
	@Setter
	private String apiUrl;
	@Setter
	private String apiMethod;
	private Long queryCounts = 0L;
	private Long queryTime = 0L;

	public void queryCountup() {
		queryCounts++;
	}

	public void addQueryTime(final Long queryTime) {
		this.queryTime += queryTime;
	}
}
