package space.yjeong.util;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class EnumValue {

    private String key;
    private String title;

    @Builder
    public EnumValue(String key, String title) {
        this.key = key;
        this.title = title;
    }

    public static EnumValue of(EnumModel enumModel) {
        return EnumValue.builder()
                .key(enumModel.getKey())
                .title(enumModel.getTitle())
                .build();
    }

    public static List<EnumValue> listOf(EnumModel[] enumModels) {
        return Arrays.stream(enumModels)
                .map(EnumValue::of)
                .collect(Collectors.toList());
    }
}