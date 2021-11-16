package khuvid19.vaccinated.SituationBoard.Data;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Map;

@XmlRootElement(name = "response")
@Getter
@Setter
public class CovidResponse {
    private Map<String, String> header;
    private Body body;

    @XmlRootElement(name = "body")
    public static class Body{
        private List<CovidResponseData> items;

        @XmlElementWrapper(name="items")
        @XmlElement(name="item")
        public List<CovidResponseData> getItems(){
            return items;
        }

        public void setItems(List<CovidResponseData> items) {
            this.items = items;
        }
    }



}

