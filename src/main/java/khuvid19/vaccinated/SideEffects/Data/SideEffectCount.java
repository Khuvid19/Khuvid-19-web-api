package khuvid19.vaccinated.SideEffects.Data;

import khuvid19.vaccinated.Constants.SideEffectType;
import khuvid19.vaccinated.Constants.VaccineType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Getter @NoArgsConstructor
public class SideEffectCount {
    @Id
    @GeneratedValue
    Long id;

    @Enumerated(value = EnumType.STRING)
    SideEffectType type;

    @Enumerated(value = EnumType.STRING)
    VaccineType vaccineType;

    int count = 0;

    public void addCount() {
        count++;
    }

    public SideEffectCount(SideEffectType type, VaccineType vaccineType) {
        this.type = type;
        this.vaccineType = vaccineType;
        this.count = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SideEffectCount that = (SideEffectCount) o;
        return type == that.type && vaccineType == that.vaccineType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, vaccineType);
    }
}
