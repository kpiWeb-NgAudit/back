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
        if (customer.getCubeIdPk() == null || customer.getCubeIdPk().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID (cubeIdPk) ne peut pas être vide pour la création.");
        }
        if (customerRepository.existsById(customer.getCubeIdPk())) {
            throw new DataIntegrityViolationException("Un client avec l'ID '" + customer.getCubeIdPk() + "' existe déjà.");
        }

        LocalDateTime now = LocalDateTime.now();
        if (customer.getCubeLastupdate() == null) {
            customer.setCubeLastupdate(now);
        }
        if (customer.getCubeLastprocess() == null) {
            customer.setCubeLastprocess(now);
        }


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


        existingCustomer.setCubeNumber(customerDetails.getCubeNumber());
        existingCustomer.setCubeName(customerDetails.getCubeName());
        existingCustomer.setCustGeocode(customerDetails.getCustGeocode());
        existingCustomer.setCustTown(customerDetails.getCustTown());
        existingCustomer.setCustCountry(customerDetails.getCustCountry());
        existingCustomer.setCustCubetype(customerDetails.getCustCubetype());
        existingCustomer.setCustOstype(customerDetails.getCustOstype());
        existingCustomer.setCustDbtype(customerDetails.getCustDbtype());
        existingCustomer.setCustErptype(customerDetails.getCustErptype());
        existingCustomer.setCustConnectStr(customerDetails.getCustConnectStr());

        existingCustomer.setCubeLastupdate(LocalDateTime.now());
        if (customerDetails.getCubeLastprocess() != null) {
            existingCustomer.setCubeLastprocess(customerDetails.getCubeLastprocess());
        }


        existingCustomer.setCubeFtpuser(customerDetails.getCubeFtpuser());
        existingCustomer.setCubeFtppasswd(customerDetails.getCubeFtppasswd());
        existingCustomer.setCustRefreshfrq(customerDetails.getCustRefreshfrq());
        existingCustomer.setCustRefreshfrqmonth(customerDetails.getCustRefreshfrqmonth());
        existingCustomer.setCustCharseparator(customerDetails.getCustCharseparator());
        existingCustomer.setCustLimitrdlfilter(customerDetails.getCustLimitrdlfilter());
        existingCustomer.setCustRdlinterwidlen(customerDetails.getCustRdlinterwidlen());
        existingCustomer.setCubeIdentity(customerDetails.getCubeIdentity());
        existingCustomer.setCustLanguage(customerDetails.getCustLanguage());
        existingCustomer.setCubeNbproddatasources(customerDetails.getCubeNbproddatasources());
        existingCustomer.setCubeProddatasourcePrefix(customerDetails.getCubeProddatasourcePrefix());
        existingCustomer.setCustBeginmonthfiscal(customerDetails.getCustBeginmonthfiscal());
        existingCustomer.setCustRdlcurrencyformat(customerDetails.getCustRdlcurrencyformat());
        existingCustomer.setCubeDailytasktrigger(customerDetails.getCubeDailytasktrigger());
        existingCustomer.setCubeLocalcubgenerate(customerDetails.getCubeLocalcubgenerate());
        existingCustomer.setCubeOptimratio(customerDetails.getCubeOptimratio());
        existingCustomer.setCubeNbdimtimevcol(customerDetails.getCubeNbdimtimevcol());
        existingCustomer.setCubeNbdimgeovcol(customerDetails.getCubeNbdimgeovcol());
        existingCustomer.setCustInternalnotes(customerDetails.getCustInternalnotes());
        existingCustomer.setCustExternalnotes(customerDetails.getCustExternalnotes());
        existingCustomer.setCustContact1(customerDetails.getCustContact1());
        existingCustomer.setCustContact2(customerDetails.getCustContact2());
        existingCustomer.setCustContact3(customerDetails.getCustContact3());
        existingCustomer.setCustShowfiscmeasureandset(customerDetails.getCustShowfiscmeasureandset());
        existingCustomer.setCustShowpctdifferencebase100(customerDetails.getCustShowpctdifferencebase100());
        existingCustomer.setCubeDimtimepkmanager(customerDetails.getCubeDimtimepkmanager());
        existingCustomer.setCubeGlobalperspective(customerDetails.getCubeGlobalperspective());
        existingCustomer.setCubeScopeMdxinstruction(customerDetails.getCubeScopeMdxinstruction());
        existingCustomer.setCubeDrillthroughnbrows(customerDetails.getCubeDrillthroughnbrows());
        existingCustomer.setCubeFactcoldefaultmeasure(customerDetails.getCubeFactcoldefaultmeasure());
        existingCustomer.setCubeDistinctcountpartition(customerDetails.getCubeDistinctcountpartition());
        existingCustomer.setCubeTypenormalreplica(customerDetails.getCubeTypenormalreplica());
        existingCustomer.setCubeParamwhenreplica(customerDetails.getCubeParamwhenreplica());
        existingCustomer.setCubeComments(customerDetails.getCubeComments());



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

            throw new DataIntegrityViolationException(
                    "Impossible de supprimer le client ID '" + customerId + "'. Il est peut-être référencé par d'autres entités (par exemple, des Facts).", e);
        }
    }
}
