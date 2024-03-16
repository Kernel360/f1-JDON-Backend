package kernel.jdon.modulebatch.jd.reader.dto;

import java.util.List;

import kernel.jdon.moduledomain.wantedjd.domain.WantedJd;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WantedJdList {
    private final List<WantedJd> wantedJdList;
}
