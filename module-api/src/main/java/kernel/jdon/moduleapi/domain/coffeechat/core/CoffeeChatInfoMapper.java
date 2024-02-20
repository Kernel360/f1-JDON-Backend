package kernel.jdon.moduleapi.domain.coffeechat.core;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import kernel.jdon.coffeechat.domain.CoffeeChat;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedSourcePolicy = ReportingPolicy.WARN
)
public interface CoffeeChatInfoMapper {

	@Mapping(source = "coffeeChat.id", target = "coffeeChatId")
	@Mapping(expression = "java(coffeeChat.getMember().getId())", target = "hostId")
	@Mapping(expression = "java(coffeeChat.getMember().getNickname())", target = "nickname")
	@Mapping(expression = "java(coffeeChat.getMember().getJobCategory().getName())", target = "job")
	@Mapping(expression = "java(coffeeChat.getCoffeeChatStatus().getActiveStatus())", target = "status")
	CoffeeChatInfo.Main of(CoffeeChat coffeeChat);
}
