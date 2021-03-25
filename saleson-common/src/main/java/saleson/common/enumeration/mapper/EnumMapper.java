package saleson.common.enumeration.mapper;


import org.reflections.Reflections;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component("enumMapper")
public class EnumMapper {
	private Map<String, List<Code>> factory = new LinkedHashMap<>();

	public EnumMapper() {
		Reflections reflections = new Reflections("saleson.common.enumeration");
		Set<Class<? extends CodeMapperType>> allClasses = reflections.getSubTypesOf(CodeMapperType.class);

		for (Class cls : allClasses) {
			String[] names = StringUtils.delimitedListToStringArray(cls.getName(), ".");
			String className = names[names.length - 1];
			put(className, cls);
		}
	}

	public void put(String key, Class<? extends CodeMapperType> e) {
		factory.put(key, toEnumValues(e));
	}

	private List<Code> toEnumValues (Class<? extends CodeMapperType> e) {
		return Arrays.stream(e.getEnumConstants())
				.map(Code::new)
				.collect(Collectors.toList());
	}

	public List<Code> get(String key) {
		return factory.get(key);
	}

	public Map<String, List<Code>> get(List<String> keys) {
		if (keys == null || keys.isEmpty()) {
			return new LinkedHashMap<>();
		}

		return keys.stream()
				.collect(Collectors.toMap(Function.identity(), key -> factory.get(key)));
	}

	public Map<String, List<Code>> getAll() {
		return factory;
	}

}
