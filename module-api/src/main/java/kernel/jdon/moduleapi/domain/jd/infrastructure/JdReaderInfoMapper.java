package kernel.jdon.moduleapi.domain.jd.infrastructure;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.moduleapi.domain.jd.core.JdInfo;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface JdReaderInfoMapper {
	JdInfo.FindWantedJd of(JdReaderInfo.FindWantedJd readerInfo);
}
