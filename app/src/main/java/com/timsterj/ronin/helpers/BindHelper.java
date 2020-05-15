package com.timsterj.ronin.helpers;

import android.content.res.Resources;

import com.timsterj.ronin.R;
import com.timsterj.ronin.common.Session;
import com.timsterj.ronin.contracts.Contracts;
import com.timsterj.ronin.model.AboutUs;
import com.timsterj.ronin.model.Poster;
import com.timsterj.ronin.data.model.Product;
import com.timsterj.ronin.model.AboutUsItem;
import com.timsterj.ronin.model.Category;
import com.timsterj.ronin.model.ProductItem;

import java.util.ArrayList;
import java.util.List;

public class BindHelper {


    public static String bindProductBaseImageUrl(String id) {
        switch (id) {
            // Роллы
            case "019901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_akari.jpg";
            case "019902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_alyaska.jpg";
            case "019903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_ananas.jpg";
            case "019904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_apelsin.jpg";
            case "019905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_asahi.jpg";
            case "019906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_bekon-s-midiyami.jpg";
            case "019907":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_blek-dzhek.jpg";
            case "019908":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_blin-roll.jpg";
            case "019909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_bol__shoy-milan.jpg";
            case "019911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_botakan.jpg";
            case "019912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_vulkan.jpg";
            case "019913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_geisha.jpg";
            case "019914":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_grecheskiy.jpg";
            case "019915":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_gril-roll.jpg";
            case "019916":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_guniku.jpg";
            case "019917":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_daniyaki.jpg";
            case "019918":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_domino.jpg";
            case "019919":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_drakon.jpg";
            case "019920":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_imperator.jpg";
            case "019921":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_imperiya.jpg";
            case "019922":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_in__-yan-2.jpg";
            case "019923":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kaliforniya.jpg";
            case "019924":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kaliforniya.jpg";
            case "019925":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kaliforniya.jpg";
            case "019926":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kaliforniya.jpg";
            case "019927":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kanada.jpg";
            case "019928":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kioto.jpg";
            case "019929":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kranch-s-krevetkoi.jpg";
            case "019930":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_krasnaya-ledi.jpg";
            case "019932":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kurasiku.jpg";
            case "019933":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_massago.jpg";
            case "019934":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_matsumoto.jpg";
            case "019935":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_mega-roll.jpg";
            case "019936":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_miyako.jpg";
            case "019937":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_nagano.jpg";
            case "019938":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_nezhnyi.jpg";
            case "019939":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_nippon.jpg";
            case "019940":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_okinava.jpg";
            case "019941":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_pikarino.jpg";
            case "019942":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_piramida.jpg";
            case "019943":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_rok-n-roll.jpg";
            case "019944":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_s-tigrovoy-krevetkoy.jpg";
            case "019946":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_sensei.jpg";
            case "019947":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_sifudo-maki.jpg";
            case "019948":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_teyshoku.jpg";
            case "019949":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tokio.jpg";
            case "019950":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_torimaki.jpg";
            case "019951":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_uzumaki.jpg";
            case "019952":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_unagi-ibusi.jpg";
            case "019953":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_unagi-ebi.jpg";
            case "019954":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_fetaki.jpg";
            case "019955":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_filadelfiya.jpg";
            case "019956":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_filadelfiya-s-avokado.jpg";
            case "019957":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_filadelfiya-s-chukoi.jpg";
            case "019958":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_filadelfiya-2.jpg";
            case "019959":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_fudzhiyama.jpg";
            case "019960":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_futo-maki.jpg";
            case "019961":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_futo-maki.jpg";
            case "019962":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_futo-maki.jpg";
            case "019963":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hayteku-roll.jpg";
            case "019964":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hokkaido.jpg";
            case "019965":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hoshi.jpg";
            case "019966":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_cezar-roll.jpg";
            case "019967":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_chernyi-segun.jpg";
            case "019968":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_shanhai.jpg";
            case "019969":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_yakudza.jpg";
            case "019970":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_yasay-roll.jpg";
            //Горячие роллы
            case "029901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_adzumi.jpg";
            case "029902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_amerikanskiy.jpg";
            case "029903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_banzay.jpg";
            case "029904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hot-bif.jpg";
            case "029905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-v-chipsah.jpg";
            case "029906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-v-chipsah.jpg";
            case "029907":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-v-chipsah.jpg";
            case "029908":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_disko-tempura.jpg";
            case "029909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_evropa.jpg";
            case "029910":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zharenaya-filadelfiya.jpg";
            case "029911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zhareniy-roll-s-lososem.jpg";
            case "029912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_yoko.jpg";
            case "029913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kaliforniya-hot.jpg";
            case "029914":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_makedoniya.jpg";
            case "029915":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_midori.jpg";
            case "029916":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_mikado.jpg";
            case "029917":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_mito-roll.jpg ";
            case "029918":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_nekka.jpg";
            case "029919":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_satori.jpg";
            case "029920":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_syake-tempura1.jpg";
            case "029921":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tori-unagi.jpg";
            case "029922":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tornado.jpg";
            case "029923":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_unagi-tempura.jpg";
            case "029924":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_fyuzhn.jpg";
            case "029925":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hanami.jpg";
            case "029926":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_harumaki.jpg";
            case "029927":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hot-bif.jpg";
            case "029928":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hot-bif.jpg";
            case "029929":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_chiken-roll.jpg";
            // Запеченные роллы
            case "039901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_dabl.jpg";
            case "039902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zapechennaya-filadelfiya.jpg";
            case "039903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zapechennye-maki.jpg";
            case "039904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zapechennye-maki.jpg";
            case "039905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zapechennye-maki.jpg";
            case "039906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zapechennye-maki.jpg";
            case "039907":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_katana.jpg";
            case "039908":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_osaka.jpg";
            case "039909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_salamandr.jpg";
            case "039910":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_samurai1.jpg";
            case "039911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tokado.jpg";
            case "039912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hatori.jpg";
            case "039913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hikaru.jpg";
            // Классические роллы
            case "049902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-ikura.jpg";
            case "049903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_rolly-s-syrom.jpg";
            case "049904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_rolly-s-syrom.jpg";
            case "049905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-s-krevetkoy.jpg";
            case "049906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-s-lososem.jpg";
            case "049908":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-s-okunem.jpg";
            case "049909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_omlet.jpg";
            case "049910":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-s-ugrem.jpg";
            case "049911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_rolly-s-syrom.jpg";
            case "049912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_roll-fresh.jpg";
            case "049913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-rolli.jpg";
            case "049914":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-rolli.jpg";
            case "049915":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-rolli.jpg";
            case "049916":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-rolli.jpg";
            case "049917":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-rolli.jpg";
            // Суши
            case "059901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_ikura.jpg";
            case "059902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_ebi.jpg";
            case "059903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_krevetka-s-syrom-filadelfiya.jpg";
            case "059904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_syake.jpg";
            case "059905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_losos-s-syrom-filadelfiya.jpg";
            case "059906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_miks-losos-mayonez-tobiko-zelenyi-luk.jpg";
            case "059907":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_miks-ugor-kurica-mayonez-tobiko.jpg";
            case "059908":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tay.jpg";
            case "059909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-kani.jpg";
            case "059910":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-ebi.jpg";
            case "059911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-syake.jpg";
            case "059912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-midii.jpg";
            case "059913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_spays-unagi.jpg";
            case "059914":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tobiko.jpg";
            case "059915":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_unagi.jpg";
            case "059916":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_ugor-s-syrom-filadelfiya.jpg";
            case "059917":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_chuka.jpg";
            case "059918":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_yaponskij-omlet.jpg";
            // Сеты
            case "069901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_goryachiy-set.jpg";
            case "069902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_devishnik.jpg";
            case "069903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_zapechennyi.jpg";
            case "069904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_laki-set.jpg";
            case "069906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_mega-set.jpg";
            case "069907":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_set-miks.jpg";
            case "069908":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_mini-goryachiy-set.jpg";
            case "069909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_set-zapechennyj-mini.jpg";
            case "069911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_harakiri.jpg";
            case "069912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_set-vegetarianskiy.jpg";
            case "069913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_imperiya-set.jpg";
            case "069914":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_set-kilogramm.jpg";
            case "069916":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_hityara.jpg";
            // Горячие закуски
            case "079901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kartofel-po-derevenski-so-speciyami.jpg";
            case "079902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kartofel-fri.jpg";
            case "079903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kolca-kalmara-v-nezhnom-klyare.jpg";
            case "079904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tigrovie-krevetki-v-panirovke.jpg";
            case "079905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/thumb_krilishki-v-assortimente.jpg";
            case "079906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/thumb_krilishki-v-assortimente.jpg";
            case "079907":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/thumb_krilishki-v-assortimente.jpg";
            case "079908":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/thumb_krilishki-v-assortimente.jpg";
            case "079909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kurinie-palochki.jpg";
            case "079910":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_syrnye-palochki.jpg";
            case "079911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_tigrovie-krevetki-v-souse-vasabi.jpg";
            case "079912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_grenki-chesnochnie-s-francuzskim-sousom.jpg";
            // Салаты
            case "089911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_cezar-s-krevetkami.JPG";
            case "089912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_cezar-s-kuricei.jpg";
            case "089904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_alpiyskiy.jpg";
            case "089913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_chuka1.jpg";
            // Wok
            case "099901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full___________.jpg";
            case "099902":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full________________________________.jpg";
            case "099903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full________________.jpg";
            case "099904":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full________________________________1.jpg";
            case "099905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full___________________________________.jpg";
            case "099906":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_______________________________.jpg";
            case "099911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full__________________________________.jpg";
            case "099912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_steklyannaya-lapsha-krevetka_kalmar.jpg";
            case "099913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_steklyannaya-lapsha-s-kuritsei.jpg";
            case "099914":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_steklyannaya-lapsha-so-svininoi.jpg";
            case "099915":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full__________________________.jpg";
            // Напитки
            case "119901":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_kola.jpg";
            case "119909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_sok-rich-v-assortimente.jpg";
            case "119910":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_sok-rich-v-assortimente.jpg";
            case "119911":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_sok-rich-v-assortimente.jpg";
            case "119912":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_sprayt.jpg";
            case "119913":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_fanta.jpg";

            // Дополнительно
            case "129903":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_vasabi.jpg";
            case "129905":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_imbir.jpg";
            case "129908":
                return "https://static-eu.insales.ru/images/products/1/5325/261526733/large_%D1%8C%D1%82%D0%BE%D0%B8%D1%80%D0%BF.jpg";
            case "129909":
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_soevyi-sous.jpg";
            case "129910":
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQa4P_Q9U5Au5RzGU7osOpL1UAvBOqqa6PVnRlMtaHvjkuby6pQ&usqp=CAU";
            case "129912":
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSFE8JyLDpqvl5cPxj07GAS2xsCY9baefCWxjH_cRT6NIhSmNnA&usqp=CAU";
            case "129914":
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT2cC9NxB8BOz6TWPcsGdmy65fpfgk7LK9xfBcg1e8miZGQ5o8S&usqp=CAU";
            case "129916":
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS5w7i5V57zwfSe-wVgttuz2HAG1oYK9LWC6wkl3Gwu096_neS5&usqp=CAU";
            case "129917":
                return "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRr30BbpoZesoTl-rWFGUcx2nnvuN9As4ywLTNQfhuEpTaACYeH&usqp=CAU";

            default:
                return "http://sushi-imperiya.ru/components/com_jshopping/files/img_products/full_noimage1.gif";
        }
    }

    public static String bindProductBaseDescription(String id) {
        return null;
    }

    public static void bindAboutUsList(List<AboutUsItem> list, Resources resources) {
        // MARK - Захардкодил, пришлось т.к сайт работает не через Api

        String[] urls = resources.getStringArray(R.array.about_us_urls);
        String[] titles = resources.getStringArray(R.array.about_us_titles);
        String[] description = resources.getStringArray(R.array.about_us_description);

        for (int i = 0; i < titles.length; i++) {

            list.add(new AboutUsItem(new AboutUs(urls[i], titles[i], description[i])));

        }

        list.add(new AboutUsItem(new Poster("http://sushi-imperiya.ru/templates/bt_education/images/skin1/html/mod_bt_slideshow/nocontact.jpg")));

    }

    public static void bindProductsBase(List<Product> products) {
        List<ProductItem> additionalList = new ArrayList<>();
        List<ProductItem> productItems = new ArrayList<>();
        List<String> categoriesName = new ArrayList<>();
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String productId = product.getId().substring(0, 2);

            if (productId.equals(Contracts.ProductsConstant.ADDITIONAL_CATEGORY)) {
                additionalList.add(new ProductItem(product));
            }

            String categoryName = bindCategoryId(productId);

            if (!categoriesName.contains(categoryName)) {
                categoriesName.add(categoryName);
                Category category = new Category(
                        categoryName,
                        i + categories.size()
                );



                categories.add(category);
                productItems.add(new ProductItem(categoryName));
                productItems.add(new ProductItem(product));
            } else {
                productItems.add(new ProductItem(product));
            }

        }

        // Category
        Session.getINSTANCE().getProducts().onNext(productItems);
        // Additional Products
        Session.getINSTANCE().getAdditionalProducts().onNext(additionalList);
        Session.getINSTANCE().getCategories().onNext(categories);

    }

    public static String bindCategoryId(String id) {
        switch (id) {
            case Contracts.ProductsConstant.ROLLS_CATEGORY:
                return Contracts.ProductsConstant.ROLLS_CATEGORY_NAME;
            case Contracts.ProductsConstant.HOT_ROLLS_CATEGORY:
                return Contracts.ProductsConstant.HOT_ROLLS_CATEGORY_NAME;
            case Contracts.ProductsConstant.BAKED_ROLLS_CATEGORY:
                return Contracts.ProductsConstant.BAKED_ROLLS_CATEGORY_NAME;
            case Contracts.ProductsConstant.CLASSIC_ROLLS_CATEGORY:
                return Contracts.ProductsConstant.CLASSIC_ROLLS_CATEGORY_NAME;
            case Contracts.ProductsConstant.SUSHI_CATEGORY:
                return Contracts.ProductsConstant.SUSHI_CATEGORY_NAME;
            case Contracts.ProductsConstant.SETS_CATEGORY:
                return Contracts.ProductsConstant.SETS_CATEGORY_NAME;
            case Contracts.ProductsConstant.SNACKS_CATEGORY:
                return Contracts.ProductsConstant.SNACKS_CATEGORY_NAME;
            case Contracts.ProductsConstant.SALADS_CATEGORY:
                return Contracts.ProductsConstant.SALADS_CATEGORY_NAME;
            case Contracts.ProductsConstant.WOK_CATEGORY:
                return Contracts.ProductsConstant.WOK_CATEGORY_NAME;
            case Contracts.ProductsConstant.DRINKS_CATEGORY:
                return Contracts.ProductsConstant.DRINKS_CATEGORY_NAME;
            case Contracts.ProductsConstant.ADDITIONAL_CATEGORY:
                return Contracts.ProductsConstant.ADDITIONAL_NAME;
            default:
                throw new IllegalArgumentException();
        }
    }


}
