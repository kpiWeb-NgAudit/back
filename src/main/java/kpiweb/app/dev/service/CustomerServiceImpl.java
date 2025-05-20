package kpiweb.app.dev.service;

import kpiweb.app.dev.entity.Customer;
import kpiweb.app.dev.exception.ResourceNotFoundException;
import kpiweb.app.dev.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        // Vérifier si un client avec cet ID existe déjà
        // (Customer.cubeIdPk n'est pas auto-généré, il doit être fourni et unique)
        if (customer.getCubeIdPk() == null || customer.getCubeIdPk().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID (cubeIdPk) ne peut pas être vide pour la création.");
        }
        if (customerRepository.existsById(customer.getCubeIdPk())) {
            // throw new DuplicateResourceException("Un client avec l'ID '" + customer.getCubeIdPk() + "' existe déjà.");
            // Alternativement, si vous n'avez pas d'exception custom pour CONFLICT :
            throw new DataIntegrityViolationException("Un client avec l'ID '" + customer.getCubeIdPk() + "' existe déjà.");
        }

        // Initialiser/Mettre à jour les champs de date si nécessaire
        // (en supposant que la DB les exige NOT NULL)
        LocalDateTime now = LocalDateTime.now();
        if (customer.getCubeLastupdate() == null) {
            customer.setCubeLastupdate(now);
        }
        if (customer.getCubeLastprocess() == null) {
            customer.setCubeLastprocess(now); // Ou une logique spécifique si différent de lastupdate
        }
        // Le champ custTimestamp (@Version) sera initialisé par JPA/DB lors du premier save

        // Ici, vous pourriez aussi initialiser d'autres champs NOT NULL qui auraient des valeurs par défaut
        // applicatives si elles ne sont pas fournies dans l'objet 'customer'
        // Exemple :
        // if (customer.getCustLanguage() == null) {
        //     customer.setCustLanguage("FR"); // Si "FR" est un défaut applicatif
        // }

        return customerRepository.save(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> getCustomerById(String customerId) {
        return customerRepository.findById(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer updateCustomer(String customerId, Customer customerDetails) {
        Customer existingCustomer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "cubeIdPk", customerId));

        // Mettre à jour les champs de l'entité existante avec les détails fournis
        // Ne pas mettre à jour la clé primaire (customerId / existingCustomer.getCubeIdPk())

        existingCustomer.setCubeNumber(customerDetails.getCubeNumber());
        existingCustomer.setCubeName(customerDetails.getCubeName());
        existingCustomer.setCustGeocode(customerDetails.getCustGeocode());
        existingCustomer.setCustTown(customerDetails.getCustTown());
        existingCustomer.setCustCountry(customerDetails.getCustCountry());
        existingCustomer.setCustCubetype(customerDetails.getCustCubetype());
        existingCustomer.setCustOstype(customerDetails.getCustOstype()); // Peut être null
        existingCustomer.setCustDbtype(customerDetails.getCustDbtype()); // Peut être null
        existingCustomer.setCustErptype(customerDetails.getCustErptype());
        existingCustomer.setCustConnectStr(customerDetails.getCustConnectStr());

        // Toujours mettre à jour cubeLastupdate lors d'une modification
        existingCustomer.setCubeLastupdate(LocalDateTime.now());
        // cubeLastprocess peut ou non être mis à jour ici, selon la logique métier
        // Si l'utilisateur peut le fournir pour une mise à jour :
        if (customerDetails.getCubeLastprocess() != null) {
            existingCustomer.setCubeLastprocess(customerDetails.getCubeLastprocess());
        }


        existingCustomer.setCubeFtpuser(customerDetails.getCubeFtpuser());
        existingCustomer.setCubeFtppasswd(customerDetails.getCubeFtppasswd());
        existingCustomer.setCustRefreshfrq(customerDetails.getCustRefreshfrq());
        existingCustomer.setCustRefreshfrqmonth(customerDetails.getCustRefreshfrqmonth());
        existingCustomer.setCustCharseparator(customerDetails.getCustCharseparator()); // Peut être null
        existingCustomer.setCustLimitrdlfilter(customerDetails.getCustLimitrdlfilter());
        existingCustomer.setCustRdlinterwidlen(customerDetails.getCustRdlinterwidlen());
        existingCustomer.setCubeIdentity(customerDetails.getCubeIdentity());
        existingCustomer.setCustLanguage(customerDetails.getCustLanguage());
        existingCustomer.setCubeNbproddatasources(customerDetails.getCubeNbproddatasources());
        existingCustomer.setCubeProddatasourcePrefix(customerDetails.getCubeProddatasourcePrefix()); // Peut être null
        existingCustomer.setCustBeginmonthfiscal(customerDetails.getCustBeginmonthfiscal());
        existingCustomer.setCustRdlcurrencyformat(customerDetails.getCustRdlcurrencyformat());
        existingCustomer.setCubeDailytasktrigger(customerDetails.getCubeDailytasktrigger());
        existingCustomer.setCubeLocalcubgenerate(customerDetails.getCubeLocalcubgenerate());
        existingCustomer.setCubeOptimratio(customerDetails.getCubeOptimratio()); // Peut être null
        existingCustomer.setCubeNbdimtimevcol(customerDetails.getCubeNbdimtimevcol());
        existingCustomer.setCubeNbdimgeovcol(customerDetails.getCubeNbdimgeovcol());
        existingCustomer.setCustInternalnotes(customerDetails.getCustInternalnotes()); // Peut être null
        existingCustomer.setCustExternalnotes(customerDetails.getCustExternalnotes()); // Peut être null
        existingCustomer.setCustContact1(customerDetails.getCustContact1()); // Peut être null
        existingCustomer.setCustContact2(customerDetails.getCustContact2()); // Peut être null
        existingCustomer.setCustContact3(customerDetails.getCustContact3()); // Peut être null
        existingCustomer.setCustShowfiscmeasureandset(customerDetails.getCustShowfiscmeasureandset());
        existingCustomer.setCustShowpctdifferencebase100(customerDetails.getCustShowpctdifferencebase100());
        existingCustomer.setCubeDimtimepkmanager(customerDetails.getCubeDimtimepkmanager());
        existingCustomer.setCubeGlobalperspective(customerDetails.getCubeGlobalperspective());
        existingCustomer.setCubeScopeMdxinstruction(customerDetails.getCubeScopeMdxinstruction()); // Peut être null
        existingCustomer.setCubeDrillthroughnbrows(customerDetails.getCubeDrillthroughnbrows());
        existingCustomer.setCubeFactcoldefaultmeasure(customerDetails.getCubeFactcoldefaultmeasure()); // Peut être null
        existingCustomer.setCubeDistinctcountpartition(customerDetails.getCubeDistinctcountpartition());
        existingCustomer.setCubeTypenormalreplica(customerDetails.getCubeTypenormalreplica());
        existingCustomer.setCubeParamwhenreplica(customerDetails.getCubeParamwhenreplica()); // Peut être null
        existingCustomer.setCubeComments(customerDetails.getCubeComments()); // Peut être null

        // Le champ custTimestamp (@Version) sera géré par JPA lors du save()
        // pour détecter les modifications concurrentes.

        return customerRepository.save(existingCustomer);
    }

    @Override
    @Transactional
    public void deleteCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "cubeIdPk", customerId));

        // Attention: Logique de suppression en cascade ou gestion des Facts liés.
        // Si des Facts référencent ce Customer et qu'il n'y a pas de ON DELETE CASCADE,
        // cette suppression échouera avec une DataIntegrityViolationException.
        // Vous pourriez avoir besoin de supprimer/délier les Facts d'abord, ou de configurer
        // la cascade au niveau DB ou JPA (ex: @OneToMany(mappedBy="customer", cascade=CascadeType.REMOVE) dans Customer)
        // ce qui peut être dangereux si non voulu.

        try {
            customerRepository.delete(customer);
        } catch (DataIntegrityViolationException e) {
            // Log l'erreur spécifique (e.g., FK violation)
            // e.printStackTrace(); // Pour le débogage
            throw new DataIntegrityViolationException(
                    "Impossible de supprimer le client ID '" + customerId + "'. Il est peut-être référencé par d'autres entités (par exemple, des Facts).", e);
        }
    }
}
