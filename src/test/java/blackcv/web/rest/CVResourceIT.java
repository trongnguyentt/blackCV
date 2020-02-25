package blackcv.web.rest;

import blackcv.BlackcvApp;
import blackcv.domain.CV;
import blackcv.repository.CVRepository;
import blackcv.service.CVService;
import blackcv.service.dto.CVDTO;
import blackcv.service.mapper.CVMapper;
import blackcv.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static blackcv.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CVResource} REST controller.
 */
@SpringBootTest(classes = BlackcvApp.class)
public class CVResourceIT {

    private static final Integer DEFAULT_I_D = 1;
    private static final Integer UPDATED_I_D = 2;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_JOB = "AAAAAAAAAA";
    private static final String UPDATED_JOB = "BBBBBBBBBB";

    private static final String DEFAULT_AVATAR = "AAAAAAAAAA";
    private static final String UPDATED_AVATAR = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_UPLOAD_CV = "AAAAAAAAAA";
    private static final String UPDATED_FILE_UPLOAD_CV = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    @Autowired
    private CVRepository cVRepository;

    @Autowired
    private CVMapper cVMapper;

    @Autowired
    private CVService cVService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCVMockMvc;

    private CV cV;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CVResource cVResource = new CVResource(cVService);
        this.restCVMockMvc = MockMvcBuilders.standaloneSetup(cVResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CV createEntity(EntityManager em) {
        CV cV = new CV()
            .iD(DEFAULT_I_D)
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .address(DEFAULT_ADDRESS)
            .job(DEFAULT_JOB)
            .avatar(DEFAULT_AVATAR)
            .fileUploadCV(DEFAULT_FILE_UPLOAD_CV)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return cV;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CV createUpdatedEntity(EntityManager em) {
        CV cV = new CV()
            .iD(UPDATED_I_D)
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .job(UPDATED_JOB)
            .avatar(UPDATED_AVATAR)
            .fileUploadCV(UPDATED_FILE_UPLOAD_CV)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return cV;
    }

    @BeforeEach
    public void initTest() {
        cV = createEntity(em);
    }

    @Test
    @Transactional
    public void createCV() throws Exception {
        int databaseSizeBeforeCreate = cVRepository.findAll().size();

        // Create the CV
        CVDTO cVDTO = cVMapper.toDto(cV);
        restCVMockMvc.perform(post("/api/cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cVDTO)))
            .andExpect(status().isCreated());

        // Validate the CV in the database
        List<CV> cVList = cVRepository.findAll();
        assertThat(cVList).hasSize(databaseSizeBeforeCreate + 1);
        CV testCV = cVList.get(cVList.size() - 1);
        assertThat(testCV.getiD()).isEqualTo(DEFAULT_I_D);
        assertThat(testCV.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCV.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testCV.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCV.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testCV.getJob()).isEqualTo(DEFAULT_JOB);
        assertThat(testCV.getAvatar()).isEqualTo(DEFAULT_AVATAR);
        assertThat(testCV.getFileUploadCV()).isEqualTo(DEFAULT_FILE_UPLOAD_CV);
        assertThat(testCV.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testCV.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCV.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void createCVWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cVRepository.findAll().size();

        // Create the CV with an existing ID
        cV.setId(1L);
        CVDTO cVDTO = cVMapper.toDto(cV);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCVMockMvc.perform(post("/api/cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cVDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CV in the database
        List<CV> cVList = cVRepository.findAll();
        assertThat(cVList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCVS() throws Exception {
        // Initialize the database
        cVRepository.saveAndFlush(cV);

        // Get all the cVList
        restCVMockMvc.perform(get("/api/cvs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cV.getId().intValue())))
            .andExpect(jsonPath("$.[*].iD").value(hasItem(DEFAULT_I_D)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].job").value(hasItem(DEFAULT_JOB)))
            .andExpect(jsonPath("$.[*].avatar").value(hasItem(DEFAULT_AVATAR)))
            .andExpect(jsonPath("$.[*].fileUploadCV").value(hasItem(DEFAULT_FILE_UPLOAD_CV)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }
    
    @Test
    @Transactional
    public void getCV() throws Exception {
        // Initialize the database
        cVRepository.saveAndFlush(cV);

        // Get the cV
        restCVMockMvc.perform(get("/api/cvs/{id}", cV.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cV.getId().intValue()))
            .andExpect(jsonPath("$.iD").value(DEFAULT_I_D))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.job").value(DEFAULT_JOB))
            .andExpect(jsonPath("$.avatar").value(DEFAULT_AVATAR))
            .andExpect(jsonPath("$.fileUploadCV").value(DEFAULT_FILE_UPLOAD_CV))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingCV() throws Exception {
        // Get the cV
        restCVMockMvc.perform(get("/api/cvs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCV() throws Exception {
        // Initialize the database
        cVRepository.saveAndFlush(cV);

        int databaseSizeBeforeUpdate = cVRepository.findAll().size();

        // Update the cV
        CV updatedCV = cVRepository.findById(cV.getId()).get();
        // Disconnect from session so that the updates on updatedCV are not directly saved in db
        em.detach(updatedCV);
        updatedCV
            .iD(UPDATED_I_D)
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .address(UPDATED_ADDRESS)
            .job(UPDATED_JOB)
            .avatar(UPDATED_AVATAR)
            .fileUploadCV(UPDATED_FILE_UPLOAD_CV)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        CVDTO cVDTO = cVMapper.toDto(updatedCV);

        restCVMockMvc.perform(put("/api/cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cVDTO)))
            .andExpect(status().isOk());

        // Validate the CV in the database
        List<CV> cVList = cVRepository.findAll();
        assertThat(cVList).hasSize(databaseSizeBeforeUpdate);
        CV testCV = cVList.get(cVList.size() - 1);
        assertThat(testCV.getiD()).isEqualTo(UPDATED_I_D);
        assertThat(testCV.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCV.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testCV.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCV.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testCV.getJob()).isEqualTo(UPDATED_JOB);
        assertThat(testCV.getAvatar()).isEqualTo(UPDATED_AVATAR);
        assertThat(testCV.getFileUploadCV()).isEqualTo(UPDATED_FILE_UPLOAD_CV);
        assertThat(testCV.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testCV.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCV.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCV() throws Exception {
        int databaseSizeBeforeUpdate = cVRepository.findAll().size();

        // Create the CV
        CVDTO cVDTO = cVMapper.toDto(cV);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCVMockMvc.perform(put("/api/cvs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cVDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CV in the database
        List<CV> cVList = cVRepository.findAll();
        assertThat(cVList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCV() throws Exception {
        // Initialize the database
        cVRepository.saveAndFlush(cV);

        int databaseSizeBeforeDelete = cVRepository.findAll().size();

        // Delete the cV
        restCVMockMvc.perform(delete("/api/cvs/{id}", cV.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CV> cVList = cVRepository.findAll();
        assertThat(cVList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
