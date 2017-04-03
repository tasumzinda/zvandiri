package zw.org.zvandiri.business.domain;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

/**
 * Created by Tasunungurwa Muzinda on 12/16/2016.
 */
@Table(name = "patient_disability_category", id = "_id")
public class PatientDisabilityCategoryContract extends Model {

    @Expose
    @Column(name = "patient_id")
    public Patient patient;

    @Expose
    @Column(name = "disability_category_id")
    public DisabilityCategory disabilityCategory;

    @Expose
    @Column(name = "id")
    public String id;

    public PatientDisabilityCategoryContract() {
        super();
    }
}
