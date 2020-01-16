package zw.org.zvandiri.business.domain;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.Expose;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import zw.org.zvandiri.business.domain.util.ReferalType;
import zw.org.zvandiri.business.util.AppUtil;
import zw.org.zvandiri.business.util.DateUtil;

import java.io.Serializable;
import java.util.*;

/**
 * Created by Tasunungurwa Muzinda on 12/17/2016.
 */
@Table(name = "services_referred", id = "_id")
public class ServicesReferred extends Model implements Serializable{

    @Expose
    @Column(name = "uuid")
    public String uuid;

    @Expose
    @Column(name = "date_created")
    public Date dateCreated;

    @Expose
    @Column(name = "date_modified")
    public Date dateModified;

    @Expose
    @Column(name = "version")
    public Long version;

    @Expose
    @Column(name = "active")
    public Boolean active = Boolean.TRUE;

    @Expose
    @Column(name = "deleted")
    public Boolean deleted = Boolean.FALSE;

    @Expose
    @Column(name = "id")
    public String id;

    @Expose
    @Column(name = "name")
    public String name;

    @Expose
    @Column(name = "description")
    public String description;

    @Expose
    @Column(name = "referalType")
    public ReferalType referalType;

    public ServicesReferred() {
        super();
    }

    public ServicesReferred(String id) {
        super();
        this.id = id;
    }

    public static ServicesReferred getItem(String id) {
        return new Select()
                .from(ServicesReferred.class).where("id = ?", id).executeSingle();
    }

    public static List<ServicesReferred> getAll() {
        return new Select()
                .from(ServicesReferred.class)
                .orderBy("name ASC")
                .execute();
    }

    public static List<ServicesReferred> getByType(ReferalType referalType){
        Log.d("Code", "code " + referalType.getCode());
        return new Select()
                .from(ServicesReferred.class)
                .where("referalType = ?", referalType)
                .execute();
    }

    public static List<ServicesReferred> findByReferral(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralServicesReferredContract.class)
                .on("referral_service.service_id = services_referred._id")
                .where("referral_service.referral_id = ?", referral.getId())
                .execute();
    }

    public static List<ServicesReferred> HivStiReq(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralHivStiServicesReqContract.class)
                .on("referral_hiv_sti_req.service_id = services_referred._id")
                .where("referral_hiv_sti_req.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> HivStiAvailed(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralHivStiServicesAvailedContract.class)
                .on("referral_hiv_sti_availed.service_id = services_referred._id")
                .where("referral_hiv_sti_availed.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> LabAvailed(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralLaboratoryAvailedContract.class)
                .on("referral_laboratory_availed.service_id = services_referred._id")
                .where("referral_laboratory_availed.referral_id = ?", referral.getId())
                .execute();
    }

    public static List<ServicesReferred> LabReq(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralLaboratoryReqContract.class)
                .on("referral_laboratory_req.service_id = services_referred._id")
                .where("referral_laboratory_req.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> LegalAvailed(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralLegalAvailedContract.class)
                .on("referral_legalb_availed.service_id = services_referred._id")
                .where("referral_legalb_availed.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> LegalReq(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralLegalReqContract.class)
                .on("referral_legal_req.service_id = services_referred._id")
                .where("referral_legal_req.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> OIArtAvailed(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralOIArtAvailedContract.class)
                .on("referral_oi_art_availed.service_id = services_referred._id")
                .where("referral_oi_art_availed.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> OIArtReq(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralOIArtReqContract.class)
                .on("referral_oi_art_req.service_id = services_referred._id")
                .where("referral_oi_art_req.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> PsychAvailed(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralPsychAvailedContract.class)
                .on("referral_psychb_availed.service_id = services_referred._id")
                .where("referral_psychb_availed.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> PsychReq(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralPsychReqContract.class)
                .on("referral_psych_req.service_id = services_referred._id")
                .where("referral_psych_req.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> SrhAvailed(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralSrhAvailedContract.class)
                .on("referral_srh_availed.service_id = services_referred._id")
                .where("referral_srh_availed.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> SrhReq(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralSrhReqContract.class)
                .on("referral_srh_req.service_id = services_referred._id")
                .where("referral_srh_req.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> TbAvailed(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralTbAvailedContract.class)
                .on("referral_tb_availed.service_id = services_referred._id")
                .where("referral_tb_availed.referral_id = ?", referral.getId())
                .execute();
    }
    public static List<ServicesReferred> TbReq(Referral referral){
        return new Select()
                .from(ServicesReferred.class)
                .innerJoin(ReferralTbReqContact.class)
                .on("referral_tb_req.service_id = services_referred._id")
                .where("referral_tb_req.referral_id = ?", referral.getId())
                .execute();
    }

    public static void deleteItem(String id) {
        new Delete().from(ServicesReferred.class).where("id = ?", id).executeSingle();
    }

    public static void deleteAll() {
        new Delete().from(ServicesReferred.class).execute();
    }

    @Override
    public String toString() {
        return name;
    }

    public static void fetchRemote(Context context, final String userName, final String password) {
        String URL = AppUtil.getBaseUrl(context) + "/static/service-referred";
        JsonArrayRequest stringRequest = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try{
                                JSONObject jsonObject = response.getJSONObject(i);
                                ServicesReferred item = new ServicesReferred();
                                item.id = jsonObject.getString("id");
                                item.active = jsonObject.getBoolean("active");
                                if(! jsonObject.isNull("dateModified")){
                                    item.dateModified = DateUtil.getFromString(jsonObject.getString("dateModified"));
                                }
                                String date = jsonObject.getString("dateCreated");
                                item.dateCreated = DateUtil.getFromString(date);
                                item.deleted = jsonObject.getBoolean("deleted");
                                item.description = jsonObject.getString("description");
                                item.name = jsonObject.getString("name");
                                item.version = jsonObject.getLong("version");
                                if( ! jsonObject.isNull("referalType")){
                                    item.referalType = ReferalType.valueOf(jsonObject.getString("referalType"));
                                }
                                ServicesReferred checkDuplicate = ServicesReferred.getItem(jsonObject.getString("id"));
                                if(checkDuplicate == null){
                                    item.save();
                                }

                            }catch (JSONException ex){
                                Log.d("item", ex.getMessage());
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("item", error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put(
                        "Authorization",
                        String.format("Basic %s", Base64.encodeToString(
                                String.format("%s:%s", userName, password).getBytes(), Base64.DEFAULT)));
                params.put("Content-Type", "application/json; charset=UTF-8");
                return params;
            }

            public Priority getPriority(){
                return Priority.NORMAL;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F));
        AppUtil.getInstance(context).addToRequestQueue(stringRequest);
    }
}
