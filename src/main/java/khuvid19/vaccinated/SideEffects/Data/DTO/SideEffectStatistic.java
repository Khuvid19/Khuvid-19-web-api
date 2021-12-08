package khuvid19.vaccinated.SideEffects.Data.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class SideEffectStatistic {
    private Integer totalPeopleCount;
    private Map<String, Integer> sideEffects;
}
