package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;
import zw.org.zvandiri.business.domain.util.TbSymptom;

import java.util.List;

@Table(name = "tb_ipt_symptom", id = "_id")
public class TbIptTbSymptomContract extends Model {

    @Column(name = "tb_ipt")
    public TbIpt tbIpt;

    @Expose
    @Column(name = "symptom")
    public TbSymptom tbSymptom;

    @Expose
    @Column(name = "id")
    public String id;

    public TbIptTbSymptomContract() {
        super();
    }

    public static List<TbIptTbSymptomContract> findByTbIpt(Contact c){
        return new Select()
                .from(TbIptTbSymptomContract.class)
                .where("tb_ipt = ?", c.getId())
                .execute();
    }
}
