package com.dt.cms.lead;

import com.dt.cms.commons.model.LeadLoggingDTO;
import com.dt.cms.lead.constants.LeadConstant;
import com.dt.cms.lead.flows.prolongation.api.CloudContractController;
import com.dt.cms.lead.flows.prolongation.api.ProlongationApiController;
import com.dt.cms.lead.flows.prolongation.entity.UserLead;
import com.dt.cms.lead.flows.prolongation.enums.Environment;
import com.dt.cms.lead.logging.context.LeadContext;
import com.dt.cms.lead.mongo.clients.UserLeadMongoClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@DirtiesContext
@AutoConfigureMessageVerifier
public class BaseTestClass {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CloudContractController cloudContractController;

    @Autowired
    private ProlongationApiController prolongationApiController;

    @Autowired
    private UserLeadMongoClient userLeadMongoClient;

    @Before
    public void setup() throws JsonProcessingException {
        StandaloneMockMvcBuilder standaloneMockMvcBuilder
                = MockMvcBuilders.standaloneSetup(webApplicationContext, cloudContractController, prolongationApiController);
        setUpContext();
        setUpLeadData();
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
    }


    private void setUpContext(){
        LeadLoggingDTO loggingInfo = new LeadLoggingDTO();
        loggingInfo.setLeadEnvironment(LeadConstant.LEAD_ENVIRONMENT_PRODUCTION);
        loggingInfo.setNatCoCode("hu");
        LeadContext.setLeadLoggingInfo(loggingInfo);
    }

    private void setUpLeadData() throws JsonProcessingException {
        UserLead userLead = new UserLead();
        userLead.setData("LUR43o1E/zSVGNxYrbp1+n3ve2E0d5fQJdNT8RGjrX+GNnO2QYGQQZSTIbVjKsecaWZN42nFarzGdT0rs90t7HwK8wtVp4hn4UWFZj2EreQEA/bue+Sb45mzyXJN7YeIUMh6blXb3l1BwWHmDpuV21K6sO8B2MbffH5ym7jYp1/rZ7Zsekm2YBTcGHmWcSLMZW2ULtQT6Rg6TkjirC6GWLhCntBvAb2E6ctB8lf5S9rF6tm+rglo3gE2dRPLAuLVTBMN5a2NaJ9ineS52nE6P5Z/kXG9xOn54kez6649SwKYf7lS6eIfe+wg+33Aolzg2AwgnlBZiqbUDg8Rlxq2UFlvH5Lq65Hibq3d0R6MJTZIHAVL7OZr6X9mYpQ0fW3Xh7UkQd2XE/wJBcUikehRUkvRhSCJPbKLBmYCztjSiRtdeX/tHfwapZ33sXAvjygtxiS+UmdFhpm4WAPhHKDWY8xxecM6pHHzXX+f97IrILW0dUmgDL2Z9mlIn8A17q2qwco3ZEJCCCLDl6SYoxE4+P5Kb1NjIYv1wG+qbFluMFXWzebcDN4Z9xRA0v4MnSBBkal9JStoHNnl8Lz79YbuGzKeS2SmVxL2fNlxUPdTihbcQAH+69w8wOAv6xWBAbKtdCxV/dUI/PII26AdX4Y7I31/H9Ww6fmrx5vCyu+2hEo/sFR1DXEFnGeWGyWZqA/Hgoz40/xXwbCO78tlMZVH2/n4w2ozsL7ukOTPfo6LI2e9N1l7luASmpJh/PsclaZoMFPsjThrxA3Mq15MvSBXna9N6uI+AmYcnlcEKxVJLIK6Dj2G0GSY3U0/PREM1/wn3ZuChT0Oc8m6p+MpR6AjAoY7ioCjO1W9E3eBh/GsKrSByD4IQKwtZ8Y/HcQMBu1QI9EnzX/DJinDGCK+CfRPVV7VxKmBiOL6pMbUXYb4ewVgNksLk1rCez55YtWoydvytEHcVlSwgd/Ms8nuV4D2Q4uk3B6rQqwpVNwOX4NnMaNQz71Na1D9BKlsFMbatshk0lV5Sp5/9rl/t9FYEDuxgg3/LuSuyhFdxnLWSDjyHclxMeGZQdVBbQP/YXG0o1wZ0bsgNvJ11PSAmvtIzDW9OcCxJmLvkxiiw7eOirnBlrPPZsLZK6nDGVZeL8jjP1YKv8kYVIFlJL/n22hL/q5Ucilonch3UXrNQ6XMlHDmX6expEHOhK4sBUra3U3POkkj5hdWFJsCCJ4gFVbUnvPHJYypKPWXTmsPbm7LnpBmJTowg8YZf7qx0tt1Xy2zpbPOkQnHCjvOIEcK7Vk4NTmEbyp87yWuozJ7lpQk1+bs6CJjSgoUU4Kj+cyvv+iE4Q2P3SP8n3tzSI2+zWPExyHJNOnV2PUJUhWZWZuCrlkM8BnDEsdCZAFqx1gOQLkMHyrF7S23E6HZjsZntUVr1YZGUIlaY4e+5vl4JsFiKQV02g0zli3YnuxcHkMz+XhqiMf7GKHkdynsBh1H4LKZyiksa7AFEeV4nMpwB3WMGPkQXRHb3o7ydfWrIr0ltktHjTWHJi0J302JIOEtVPMXKfYQWKZEuPMSZ3JD1zxaecSnq7vVbWxslsSz5bx4FTyplAF1Q/unLuABf2ypFYLtKpasug0TsTW7mNAd29Q1Hi89E+UIF8xHZQ/XOI8gKfkEGXdR7OFcjaI+j77yXzjPxG9eyXnmbdy7qGiaqFSQjQ5UTH8UT+e4690o8D3WVYspC5WDGmKLYdsWy6/CT+4QJikQn98yonubTbUWnX+RaZeUFExIR0VHMRs5fW9R0QLY1X01BmkQHXEXuON0T8TCnqHLGneEaPm1Hm88E5gIXuQJm7isAOUUJ3DBFz4hxtE/12EoS9dwFcb8j1+39EieZsX8D9MKOZNu+QhWQfNpzvO26ft0gmJrUieFhufRGPEtbleTi0ihQnVX4zddWKZOcSd4FQ==");
        userLead.setNatCoKey("hu");
        userLead.setUuid("8e03cc0d-b06a-4835-b666-51893db6da02");
        userLead.setEncKey("LUR43o1E/zSVGNxYrbp1+n3ve2E0d5fQJdNT8RGjrX+GNnO2QYGQQZSTIbVjKsecaWZN42nFarzGdT0rs90t7HwK8wtVp4hn4UWFZj2EreQEA/bue+Sb45mzyXJN7YeIUMh6blXb3l1BwWHmDpuV21K6sO8B2MbffH5ym7jYp1/rZ7Zsekm2YBTcGHmWcSLMZW2ULtQT6Rg6TkjirC6GWLhCntBvAb2E6ctB8lf5S9rF6tm+rglo3gE2dRPLAuLVTBMN5a2NaJ9ineS52nE6P5Z/kXG9xOn54kez6649SwKYf7lS6eIfe+wg+33Aolzg2AwgnlBZiqbUDg8Rlxq2UFlvH5Lq65Hibq3d0R6MJTZIHAVL7OZr6X9mYpQ0fW3Xh7UkQd2XE/wJBcUikehRUkvRhSCJPbKLBmYCztjSiRtdeX/tHfwapZ33sXAvjygtxiS+UmdFhpm4WAPhHKDWY8xxecM6pHHzXX+f97IrILW0dUmgDL2Z9mlIn8A17q2qwco3ZEJCCCLDl6SYoxE4+P5Kb1NjIYv1wG+qbFluMFXWzebcDN4Z9xRA0v4MnSBBkal9JStoHNnl8Lz79YbuGzKeS2SmVxL2fNlxUPdTihbcQAH+69w8wOAv6xWBAbKtdCxV/dUI/PII26AdX4Y7I31/H9Ww6fmrx5vCyu+2hEo/sFR1DXEFnGeWGyWZqA/Hgoz40/xXwbCO78tlMZVH2/n4w2ozsL7ukOTPfo6LI2e9N1l7luASmpJh/PsclaZoMFPsjThrxA3Mq15MvSBXna9N6uI+AmYcnlcEKxVJLIK6Dj2G0GSY3U0/PREM1/wn3ZuChT0Oc8m6p+MpR6AjAoY7ioCjO1W9E3eBh/GsKrSByD4IQKwtZ8Y/HcQMBu1QI9EnzX/DJinDGCK+CfRPVV7VxKmBiOL6pMbUXYb4ewVgNksLk1rCez55YtWoydvytEHcVlSwgd/Ms8nuV4D2Q4uk3B6rQqwpVNwOX4NnMaNQz71Na1D9BKlsFMbatshk0lV5Sp5/9rl/t9FYEDuxgg3/LuSuyhFdxnLWSDjyHclxMeGZQdVBbQP/YXG0o1wZ0bsgNvJ11PSAmvtIzDW9OcCxJmLvkxiiw7eOirnBlrPPZsLZK6nDGVZeL8jjP1YKv8kYVIFlJL/n22hL/q5Ucilonch3UXrNQ6XMlHDmX6expEHOhK4sBUra3U3POkkj5hdWFJsCCJ4gFVbUnvPHJYypKPWXTmsPbm7LnpBmJTowg8YZf7qx0tt1Xy2zpbPOkQnHCjvOIEcK7Vk4NTmEbyp87yWuozJ7lpQk1+bs6CJjSgoUU4Kj+cyvv+iE4Q2P3SP8n3tzSI2+zWPExyHJNOnV2PUJUhWZWZuCrlkM8BnDEsdCZAFqx1gOQLkMHyrF7S23E6HZjsZntUVr1YZGUIlaY4e+5vl4JsFiKQV02g0zli3YnuxcHkMz+XhqiMf7GKHkdynsBh1H4LKZyiksa7AFEeV4nMpwB3WMGPkQXRHb3o7ydfWrIr0ltktHjTWHJi0J302JIOEtVPMXKfYQWKZEuPMSZ3JD1zxaecSnq7vVbWxslsSz5bx4FTyplAF1Q/unLuABf2ypFYLtKpasug0TsTW7mNAd29Q1Hi89E+UIF8xHZQ/XOI8gKfkEGXdR7OFcjaI+j77yXzjPxG9eyXnmbdy7qGiaqFSQjQ5UTH8UT+e4690o8D3WVYspC5WDGmKLYdsWy6/CT+4QJikQn98yonubTbUWnX+RaZeUFExIR0VHMRs5fW9R0QLY1X01BmkQHXEXuON0T8TCnqHLGneEaPm1Hm88E5gIXuQJm7isAOUUJ3DBFz4hxtE/12EoS9dwFcb8j1+39EieZsX8D9MKOZNu+QhWQfNpzvO26ft0gmJrUieFhufRGPEtbleTi0ihQnVX4zddWKZOcSd4FQ==");
        userLead.setTrackingId("tracking_id_1");
        userLeadMongoClient.save(userLead, "8e03cc0d-b06a-4835-b666-51893db6da02", "hu", Environment.PROD, LeadConstant.COLLECTION_LEADS );

    }


    @After
    public void after(){


    }
}
