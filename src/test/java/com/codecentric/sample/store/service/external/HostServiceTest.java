package com.codecentric.sample.store.service.external;

import com.codecentric.sample.store.model.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * 异常测试
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({AddressService.class})
public class HostServiceTest {

    private AddressService addressService;

    @Mock
    private ExternalSystemProxy externalSystemProxy;

    @InjectMocks
    private HostService hostService;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    // 期望是收到异常
    @Test(expected = IOException.class)
    public void testConnectionNotAvailable() throws IOException {

        //
        // Given
        //
        // 入参不受限制，模逆抛出异常
        when(externalSystemProxy.connectionAvailable(any(String.class))).thenThrow(new IOException());

        //
        // When
        //
        hostService.connect();

        //
        // Then
        //
        // Empty as we are expecting an exception to be thrown
    }

    /**
     * 修改私有方法
     */
    @Test
    public void testMockPrivate() throws Exception {

        addressService = PowerMockito.spy(new AddressService());
        PowerMockito.when(addressService, "getAnInt").thenReturn(123);
        int realResult = addressService.getPLZForCustomer(Matchers.<Customer>any());
        Assert.assertEquals(123, realResult);
    }
}
