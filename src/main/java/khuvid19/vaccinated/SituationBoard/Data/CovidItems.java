package khuvid19.vaccinated.SituationBoard.Data;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "body")
@Setter
public class CovidItems {
    private List<CovidData> items;

    @XmlElementWrapper(name="itmes")
    @XmlElement(name="item")
    public List<CovidData> getItems(){
        return items;
    }

}
