package kpiweb.app.dev.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @Column(name = "cube_id_pk", nullable = false)
    private String cubeIdPk;

    @Column(name = "cube_number", nullable = false)
    private Integer cubeNumber;

    @Column(name = "cube_name", nullable = false)
    private String cubeName;

    @Column(name = "cust_geocode", nullable = false)
    private String custGeocode;

    @Column(name = "cust_town", nullable = false)
    private String custTown;

    @Column(name = "cust_country", nullable = false)
    private String custCountry;

    @Column(name = "cust_cubetype", nullable = false)
    private String custCubetype;

    @Column(name = "cust_ostype")
    private String custOstype;

    @Column(name = "cust_dbtype")
    private String custDbtype;

    @Column(name = "cust_erptype", nullable = false)
    private String custErptype;

    @Column(name = "cust_connect_str", nullable = false)
    private String custConnectStr;

    @Column(name = "cube_lastupdate" , nullable = false)
    private LocalDateTime cubeLastupdate;

    @Column(name = "cube_lastprocess", nullable = false)
    private LocalDateTime cubeLastprocess;

    @Column(name = "cube_ftpuser", nullable = false)
    private String cubeFtpuser;

    @Column(name = "cube_ftppasswd", nullable = false)
    private String cubeFtppasswd;

    @Column(name = "cust_refreshfrq", nullable = false)
    private String custRefreshfrq;

    @Column(name = "cust_refreshfrqmonth" , nullable = false)
    private String custRefreshfrqmonth; // <--- CHANGED FROM Integer to String

    @Column(name = "cust_charseparator")
    private String custCharseparator;

    @Column(name = "cust_limitrdlfilter" , nullable = false)
    private Integer custLimitrdlfilter;

    @Column(name = "cust_rdlinterwidlen" , nullable = false)
    private String custRdlinterwidlen;

    @Column(name = "cube_identity" , nullable = false)
    private String cubeIdentity;

    @Column(name = "cust_language", nullable = false)
    private String custLanguage;

    @Column(name = "cube_nbproddatasources", nullable = false)
    private Integer cubeNbproddatasources;

    @Column(name = "cube_proddatasource_prefix")
    private String cubeProddatasourcePrefix;

    @Column(name = "cust_beginmonthfiscal" , nullable = false)
    private Integer custBeginmonthfiscal;

    @Column(name = "cust_rdlcurrencyformat" , nullable = false)
    private String custRdlcurrencyformat;

    @Column(name = "cube_dailytasktrigger" , nullable = false)
    private String cubeDailytasktrigger;

    @Column(name = "cube_localcubgenerate" , nullable = false)
    private String cubeLocalcubgenerate;

    @Column(name = "cube_optimratio")
    private String cubeOptimratio;

    @Column(name = "cube_nbdimtimevcol", nullable = false)
    private Integer cubeNbdimtimevcol = 0;

    @Column(name = "cube_nbdimgeovcol", nullable = false)
    private Integer cubeNbdimgeovcol = 0;

    @Column(name = "cust_internalnotes", columnDefinition = "TEXT")
    private String custInternalnotes;

    @Column(name = "cust_externalnotes", columnDefinition = "TEXT")
    private String custExternalnotes;

    @Column(name = "cust_contact1", length = 255)
    private String custContact1;

    @Column(name = "cust_contact2", length = 255)
    private String custContact2;

    @Column(name = "cust_contact3", length = 255)
    private String custContact3;

    @Column(name = "cust_showfiscmeasureandset" , nullable = false , length = 5)
    private String  custShowfiscmeasureandset;

    @Column(name = "cust_showpctdifferencebase100" , nullable = false, length = 5)
    private String  custShowpctdifferencebase100;

    @Column(name = "cube_dimtimepkmanager" , nullable = false)
    private String cubeDimtimepkmanager;

    @Column(name = "cube_globalperspective" , nullable = false)
    private String cubeGlobalperspective;

    @Column(name = "cube_scope_mdxinstruction", columnDefinition = "TEXT")
    private String cubeScopeMdxinstruction;

    @Column(name = "cube_drillthroughnbrows", nullable = false)
    private Integer cubeDrillthroughnbrows = 0;

    @Column(name = "cube_factcoldefaultmeasure")
    private String cubeFactcoldefaultmeasure;

    @Column(name = "cube_distinctcountpartition" , nullable = false)
    private String cubeDistinctcountpartition;

    @Column(name = "cube_typenormalreplica" , nullable = false)
    private String cubeTypenormalreplica;

    @Column(name = "cube_paramwhenreplica")
    private String cubeParamwhenreplica;

    @Column(name = "cube_comments", columnDefinition = "TEXT")
    private String cubeComments;

    @Version
    @Column(name = "cust_timestamp",
            nullable = false,
            insertable = false,
            updatable = false)
    private byte[] custTimestamp;



    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    // mappedBy = "customer" refers to the 'customer' field in the Fact entity
    // cascade = CascadeType.ALL: operations (persist, merge, remove, etc.) on Customer will cascade to associated Facts. Use with caution.
    // orphanRemoval = true: if a Fact is removed from this collection, it will be deleted from the DB.
    // fetch = FetchType.LAZY: Facts are not loaded until explicitly accessed.
    private Set<Fact> facts = new HashSet<>();

    public void addFact(Fact fact) {
        facts.add(fact);
        fact.setCustomer(this);

    }

    public void removeFact(Fact fact) {
        facts.remove(fact);
        fact.setCustomer(null);

    }


}