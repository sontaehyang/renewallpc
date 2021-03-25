package saleson.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModelUtils {
	private ModelUtils() {
	}

	public static List<Long> getIds(String[] ids) {

        if (ids != null) {
            return Arrays.stream(ids).map(i -> Long.parseLong(i)).collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

}
