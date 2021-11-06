package khuvid19.vaccinated.SideEffects.Data;

import khuvid19.vaccinated.Constants.SideEffectType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Objects;


@Entity
@Getter @NoArgsConstructor
public class SideEffectCount {
    @Id
    @Enumerated(value = EnumType.STRING)
    SideEffectType type;

    int count = 0;


    public void addCount() {
        count++;
    }

    public SideEffectCount(SideEffectType type) {
        this.type = type;
        this.count = 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SideEffectCount that = (SideEffectCount) o;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
