package org.delivery.storeadmin.domain.store.util;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.storeadmin.domain.store.controller.model.StoreResponse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CsvUtils {

    public static List<StoreResponse> csvToStoreResponse(){

        String file = "./store-admin/store.csv";
        log.info("in");
        if (file==null) {
            file = "./service/store.csv";
            log.info("out");
        }
        List<List<String>> csvList = new ArrayList<>();
        try(CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values = null;
            while((values = csvReader.readNext())!= null){
                csvList.add(Arrays.asList(values));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error("CSV -> Store 변환 실패:{}",e);
        }
        return IntStream.range(1,csvList.size()).mapToObj(index ->{
            List<String> rowList = csvList.get(index);
            String[] split = rowList.get(2).split(",");

            return StoreResponse.builder()
                    .name(rowList.get(1))
                    .address(split[0])
                    .phoneNumber(rowList.get(6))
                    .thumbnailUrl(rowList.get(9))
                    .category(registerStoreCategory(rowList.get(7)))
                    .latitude(Double.parseDouble(rowList.get(4)))
                    .longitude(Double.parseDouble(rowList.get(5)))
                    .build();
        }).collect(Collectors.toList());
    }

    public static StoreCategory registerStoreCategory(String category){
        Set<String> koreaFood = new HashSet<>(Arrays.asList(
                "게요리","국밥","국수","낙지","냉면","닭갈비","닭요리","덮밥","두부요리집","막국수","만두","백반","백숙","보리밥","복어요리","생선구이","샤브샤브","쌈밥","장어",
                "전","칼국수","한식","한정식","해물"
        ));
        Set<String> chineseFood = new HashSet<>(Arrays.asList(
                "양꼬치","중국식","중식"
        ));
        Set<String> japaneseFood = new HashSet<>(Arrays.asList(
                "라멘","오뎅","우동","일식"
        ));
        Set<String> westernFood = new HashSet<>(Arrays.asList(
                "경양식","돈가스","브런치","양식","이탈리아음식","파스타","패밀리레스토랑","퓨전음식"
        ));
        Set<String> chicken = new HashSet<>(Arrays.asList(
                "치킨","호프/통닭"
        ));
        Set<String> pizza = new HashSet<>(Arrays.asList(
                "피자"
        ));
        Set<String> hamburger = new HashSet<>(Arrays.asList(
                "패스트푸드","핫도그"
        ));
        Set<String> cafe = new HashSet<>(Arrays.asList(
                "과일주스","다방","차","카페","커피숍"
        ));
        Set<String> bar = new HashSet<>(Arrays.asList(
                "감성주점","맥주","술집","정종/대포집/소주방","포장마차"
        ));
        Set<String> convenient = new HashSet<>(Arrays.asList(
                "기타(편의점)"
        ));
        Set<String> porkFeet = new HashSet<>(Arrays.asList(
                "족발"
        ));
        Set<String> midnightSnack = new HashSet<>(Arrays.asList(
                "곱창,막창.양","닭발","쭈꾸미"
        ));
        Set<String> meat = new HashSet<>(Arrays.asList(
                "돼지고기","소고기구이","식육(숯불구이)","오리요리","육류"
        ));
        Set<String> snack = new HashSet<>(Arrays.asList(
                "김밥","김밥(도시락)","도시락","떡볶이","분식"
        ));
        Set<String> etc = new HashSet<>(Arrays.asList(
                "고속도로","기타","극장","기타 휴게음식점","뷔페","뷔페식","음식점","일반조리판매","푸드코트","한식뷔페","호두과자"
        ));
        Set<String> jjigae = new HashSet<>(Arrays.asList(
                "감자탕","곰탕","매운탕","사철,영양탕","설렁탕","순대국","아귀찜","찌개,전골","찜닭","추어탕","탕류(보신용)","해장국"
        ));
        Set<String> sashimi = new HashSet<>(Arrays.asList(
                "생선회","조개","회집"
        ));
        Set<String> bread = new HashSet<>(Arrays.asList(
                "베이커리","샌드위치","아이스크림","제과점영업","케이크전문","토스트"
        ));
        Set<String> asianFood = new HashSet<>(Arrays.asList(
                "베트남음식","외국음식전문점(인도,태국등)"
        ));
        if (koreaFood.contains(category))
            return StoreCategory.KOREAN_FOOD;
        else if(japaneseFood.contains(category))
            return StoreCategory.JAPANESE_FOOD;
        else if(chicken.contains(category))
            return StoreCategory.CHINESE_FOOD;
        else if(westernFood.contains(category))
            return StoreCategory.WESTERN_FOOD;
        else if(chicken.contains(category))
            return StoreCategory.CHICKEN;
        else if(pizza.contains(category))
            return StoreCategory.PIZZA;
        else if(hamburger.contains(category))
            return StoreCategory.HAMBURGER;
        else if(cafe.contains(category))
            return StoreCategory.CAFE;
        else if (bar.contains(category))
            return StoreCategory.BAR;
        else if (convenient.contains(category))
            return StoreCategory.CONVENIENT;
        else if (porkFeet.contains(category))
            return StoreCategory.PORK_FEET;
        else if (midnightSnack.contains(category))
            return StoreCategory.MIDNIGHT_SNACK;
        else if (meat.contains(category))
            return StoreCategory.MEAT;
        else if (snack.contains(category))
            return StoreCategory.SNACK;
        else if (etc.contains(category))
            return StoreCategory.ETC;
        else if (jjigae.contains(category))
            return StoreCategory.JJIGAE;
        else if (sashimi.contains(category))
            return StoreCategory.SASHIMI;
        else if (bread.contains(category))
            return StoreCategory.BREAD;
        else if (asianFood.contains(category))
            return StoreCategory.ASIAN_FOOD;
        else
            return StoreCategory.ETC;
    }
}
