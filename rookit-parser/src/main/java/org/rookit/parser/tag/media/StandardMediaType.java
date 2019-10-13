package org.rookit.parser.tag.media;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@SuppressWarnings("javadoc")
public enum StandardMediaType implements MediaType {
	
	DIG,
	DIGA,
	
	ANA,
	ANAWAC,
	ANA8CA,
	
	CD,
	CDA,
	CDDD,
	CDAD,
	CDAA,
	
	LD,
	LDA,
	
	TT,
	TT33,
	TT45,
	TT71,
	TT76,
	TT78,
	TT80,
	
	MD,
	MDA,
	
	DAT,
	DATA,
	DAT1,
	DAT2,
	DAT3,
	DAT4,
	DAT5,
	DAT6,
	
	DCC,
	DCCA,
	
	DVD,
	DVDA,
	
	TV,
	TVPAL,
	TVNTSC,
	TVSECAM,
	
	VID,
	VIDPAL,
	VIDNTSC,
	VIDSECAM,
	VIDVHS,
	VIDSVHS,
	VIDBETA,
	
	RAD,
	RADFM,
	RADAM,
	RADLW,
	RADMW,
	
	TEL,
	TELI,
	
	MC,
	MC4,
	MC9,
	MCI,
	MCII,
	MCIII,
	MCIV,
	
	REE,
	REE9,
	REE19,
	REE38,
	REE76,
	REEI,
	REEII,
	REEIII,
	REEIV;
	
	@Override
	public String getCode() {
		return name();
	}
	
	public static Optional<MediaType> parseByCode(final String value) {
		if (Objects.isNull(value)) {
			return Optional.empty();
		}
		
		return Arrays.stream(values())
				.filter(type -> type.getCode().equalsIgnoreCase(value))
				.map(MediaType.class::cast)
				.findFirst();
	}

}
