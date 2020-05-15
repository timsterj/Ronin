package com.timsterj.ronin.domain.repositories;

import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.domain.api.FrontPadApi;
import com.timsterj.ronin.domain.modelDTO.ProductDTO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class RepositoryFrontPadTest {


    private FrontPadApi frontPadApi;

    private RepositoryFrontPad repositoryFrontPad;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        repositoryFrontPad = new RepositoryFrontPad();

        repositoryFrontPad.retrofit = mock(Retrofit.class);

        frontPadApi = mock(FrontPadApi.class);

        ProductDTO productDTO = getProductDTO();


        when(repositoryFrontPad.retrofit.create(FrontPadApi.class)).thenReturn(frontPadApi);

        RequestBody requestBody;

        HashMap<String, RequestBody> map = new HashMap<>();

        requestBody = RequestBody.create(MediaType.parse("text/plain"), Contracts.RetrofitConstant.FRONT_PAD_SECRET);
        map.put("secret", requestBody);

        when(frontPadApi.getProducts(map))
                .thenReturn(Observable.just(productDTO));

    }


    private ProductDTO getProductDTO() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProduct_id(new String[]{
                "019901",
                "019902",
                "019903",
                "019904",
                "019905",
                "019906",
                "019907",
                "019908",
                "019909",
                "019910",
                "019911",
                "019912",
                "019913",
                "019914",
                "019915",
                "019916",
                "019917",
                "019918",
                "019919",
                "019920",
                "019921",
                "019922",
                "019923",
                "019924",
                "019925",
                "019926",
                "019927",
                "019928",
                "019929",
                "019930",
                "019931",
                "019932",
                "019933",
                "019934",
                "019935",
                "019936",
                "019937",
                "019938",
                "019939",
                "019940",
                "019941",
                "019942",
                "019943",
                "019944",
                "019945",
                "019946",
                "019947",
                "019948",
                "019949",
                "019950",
                "019951",
                "019952",
                "019953",
                "019954",
                "019955",
                "019956",
                "019957",
                "019958",
                "019959",
                "019960",
                "019961",
                "019962",
                "019963",
                "019964",
                "019965",
                "019966",
                "019967",
                "019968",
                "019969",
                "019970"
        });

        productDTO.setName(new String[]{
                "Акари",
                "Аляска",
                "Ананас",
                "Апельсин",
                "Асахи",
                "Бекон с мидиями",
                "Блек Джек",
                "Блин ролл",
                "Большой Милан",
                "Бонито",
                "Ботакан",
                "Вулкан",
                "Гейша",
                "Греческий",
                "Гриль ролл",
                "Гунику",
                "Данияки",
                "Домино",
                "Дракон",
                "Император",
                "Империя",
                "Пусто",
                "Калифорния с крабом",
                "Калифорния с креветкой",
                "Калифорния с лососем",
                "Калифорния с угрём",
                "Канада",
                "Киото",
                "Кранч с креветкой",
                "Красная Леди",
                "Крейзи",
                "Курасику",
                "Масаго",
                "Мацумото",
                "Мега ролл",
                "Мияко",
                "Нагано",
                "Нежный",
                "Ниппон",
                "Окинава",
                "Пикарино",
                "Пирамида",
                "Рок-н-ролл",
                "Ролл с тигровой креветкой",
                "Самурай",
                "Сенсей",
                "Сифудо маки",
                "Тейшоку",
                "Токио",
                "Торимаки",
                "Узумаки",
                "Унаги Ибуси",
                "Унаги Эби",
                "Фетаки",
                "Филадельфия",
                "Филадельфия с авакадо",
                "Филадельфия с чукой",
                "Филадельфия №2",
                "Фуджияма",
                "Футомаки с креветкой",
                "Футомаки с лососем",
                "Футомаки с угрём",
                "Хайтеку",
                "Хаккайдо",
                "Хоши",
                "Цезарь ролл",
                "Черный сегун",
                "Шанхай",
                "Якудза",
                "Ясай ролл"
        });

        productDTO.setPrice(new String[]{
                "234",
                "214",
                "254",
                "204",
                "204",
                "214",
                "264",
                "234",
                "314",
                "204",
                "204",
                "264",
                "284",
                "164",
                "254",
                "224",
                "304",
                "254",
                "234",
                "314",
                "294",
                "184",
                "254",
                "254",
                "254",
                "254",
                "244",
                "274",
                "234",
                "279",
                "254",
                "204",
                "234",
                "279",
                "204",
                "254",
                "234",
                "254",
                "254",
                "234",
                "184",
                "234",
                "274",
                "174",
                "224",
                "234",
                "154",
                "184",
                "234",
                "174",
                "214",
                "254",
                "224",
                "264",
                "254",
                "254",
                "254",
                "254",
                "264",
                "184",
                "184",
                "184",
                "194",
                "214",
                "184",
                "194",
                "244",
                "214",
                "244",
                "154"
        });
        return productDTO;
    }


    @Test
    public void getFrontPadApi_Normal() {

        Object frontPadApi = repositoryFrontPad.getFrontPadApi();

        assertThat(frontPadApi).isInstanceOf(FrontPadApi.class);
    }

    @Test
    public void getProductsResponse() {
        Observable<ProductDTO> response = repositoryFrontPad.getProductsResponse();

        assertThat(response).isNotNull();

        TestObserver<ProductDTO> testObserver = new TestObserver<>();

        response.subscribe(testObserver);

        testObserver.assertValueCount(1);
        testObserver.assertNoErrors();
    }


    @Test
    public void prepareList() {

        List<Product> products = new ArrayList<>();

        ProductDTO productDTO = getProductDTO();

        repositoryFrontPad.prepareList(productDTO, products);

        assertThat(products).hasSize(70);
    }
}