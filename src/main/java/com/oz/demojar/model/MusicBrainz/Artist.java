package com.oz.demojar.model.MusicBrainz;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize

public class Artist {
    private String id;
    private String type;
    private String name;
    private String gender;
    private String country;

}
