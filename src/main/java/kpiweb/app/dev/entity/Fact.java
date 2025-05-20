package kpiweb.app.dev.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Table(name = "facts", schema = "dbo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fact {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fact_id_pk_generator")
    @SequenceGenerator(
            name = "fact_id_pk_generator",
            sequenceName = "dbo.fact_id_pk_seq",
            allocationSize = 1
    )
    @Column(name = "fact_id_pk", nullable = false, updatable = false)
    private Integer factIdPk;

    @NotBlank
    @Size(max = 20)
    @Column(name = "fact_tname", nullable = false, length = 20)
    private String factTname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fact_type", nullable = false, length = 8)
    private FactTypeEnum factType;

    @Column(name = "factdbextr_id_pk")
    private Integer factdbextrIdPk;

    @NotBlank
    @Size(max = 6)
    @Pattern(regexp = "FPROCY")
    @Column(name = "fact_proccube", nullable = false, length = 6)
    private String factProccube = "FPROCY";

    @NotBlank
    @Size(max = 20)
    @Column(name = "fact_shortcubename", nullable = false, length = 20)
    private String factShortcubename;


    @NotBlank
    @Size(max = 30)
    @Column(name = "fact_shortpresname", nullable = false, length = 30)
    private String factShortpresname;
    @NotNull
    @Column(name = "fact_workorder", nullable = false)
    private Integer factWorkorder;


    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cube_id_pk", nullable = false)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "fact_factdatafiletype", length = 10)
    private FactFactDataFileTypeEnum factFactdatafiletype;

    @Size(max = 50)
    @Column(name = "fact_factdatafilename", length = 50)
    private String factFactdatafilename;

    @Column(name = "fact_factdatafilecheckunicity")
    private Boolean factFactdatafilecheckunicity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fact_zonespe", nullable = false, length = 6)
    private FactZoneSpeEnum factZonespe;

    @NotNull
    @Column(name = "fact_lastupdate", nullable = false)
    private LocalDateTime factLastupdate;

    @Lob
    @Column(name = "fact_comments", columnDefinition = "TEXT")
    private String factComments;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "fact_partitiontype", nullable = false, length = 7)
    private FactPartitionTypeEnum  factPartitiontype;

    @Version
    @Column(name = "fact_timestamp", nullable = false, insertable = false, updatable = false)
    private byte[] factTimestamp;


    @Override
    public String toString() {
        return "Fact{" +
                "factIdPk=" + factIdPk +
                ", factTname='" + factTname + '\'' +
                ", factType=" + factType +
                ", customerId=" + (customer != null ? customer.getCubeIdPk() : "null") + // Pour éviter LazyInit
                '}';
    }
}