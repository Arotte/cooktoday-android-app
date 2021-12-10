// will change when get recipe database
package com.animee.cooktodaynew.bean;


import com.animee.cooktodaynew.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;



public class FoodUtils {
    private static final String[] food = { "pork", "Pork Liver ", "Pig's blood", "Lamb", "Beef", "Beef liver", "Goose", "Rabbit", "Dog",
            "duck", "chicken", "donkey", "egg", "carp", "yellowtail", "shrimp", "shrimp skin", "crab", "clam", "turtle meat",
            "snails", "garlic", "spring onions", "turnips", "celery", "leeks", "spinach", "lettuce", "bamboo shoots", "tomatoes", "onions",
            "vinegar", "tea", "soy milk", "brown sugar", "honey", "milk", "white wine", "beer"};
    private static final String[] food1 = {  "Yellow lotus", "Buckwheat, bird's meat, bean sprouts", "He Shou Wu, groundnut, soybean, kelp", "Vinegar, red beans, half summer, pumpkin",
            "Olives, panna cotta, leeks", "Catfish, eels, persimmons", "Dog meat, carp, mandarin oranges", "Carp, mung beans", "Turtle", "Carp", "Golden needle mushrooms",
            "Soybean milk Rabbit", "Licorice Wheatgrass", "Buckwheat noodles", "Foods rich in vitamin C", "Red dates Soya beans",
            "pears, persimmons, aubergines, peanuts, pomegranates, melons, celery, honey, tomatoes", "celery", "duck meat", "cantaloupe, wood ear, beef, broad beans, corn",
            "Dihuang, He Shou Wu, Atractylodes", "Dates", "Oranges, Wood Ear", "Cucumber, Clams, Crabs", "Beef", "Tofu, Eel, Cucumber", "Honey",
            "syrup", "white wine", "honey", "carrot", "wine", "honey", "bamboo shoots", "skinned egg", "tofu leek",
            "calcium juice drugs leek lemon", "carrot walnut beer sweet potato", "seafood"};
    private static final int[] resId = { R.mipmap.pork, R.mipmap.pigliver, R.mipmap.pigblood,
            R.mipmap.lamb, R.mipmap.beef, R.mipmap.beefliver,
            R.mipmap.goose, R.mipmap.rabbit, R.mipmap.dog,
            R.mipmap.duck, R.mipmap.chicken, R.mipmap.donkey,
            R.mipmap.egg, R.mipmap.carp, R.mipmap.yellowfish,
            R.mipmap.shrimp, R.mipmap.shrimp2, R.mipmap.crab,
            R.mipmap.clam, R.mipmap.turtle, R.mipmap.riversnail,
            R.mipmap.garlic, R.mipmap.onion, R.mipmap.radish,
            R.mipmap.celery, R.mipmap.leek, R.mipmap.spinach,
            R.mipmap.lettuce, R.mipmap.bamboo, R.mipmap.tomato,
            R.mipmap.foreignonion, R.mipmap.vinegar, R.mipmap.tea,
            R.mipmap.beanmilk, R.mipmap.brownsuger, R.mipmap.honey,
            R.mipmap.milk, R.mipmap.whitespirit, R.mipmap.beer };
    private static final String [] fooddesc={"Pork is one of the most common meat foods on people's tables today. Lean pork is rich in iron, which is easily absorbed by the body and can prevent anaemia. Pork is sweet and salty in taste, and calm in nature, with the effect of nourishing deficiency, nourishing Yin, nourishing blood and moistening dryness."
            , "Pig liver is sweet and bitter in taste, warm in nature, and belongs to the liver meridian. Pig's liver is rich in many nutrients and is the food of choice for preventing ischaemic anaemia. The iron in pig's liver is easily absorbed by the body and is ideal for nourishing the liver, brightening the eyes and nourishing the blood."
            , "Pig's blood is sweet, bitter and warm in nature, and has the effect of detoxifying and cleansing the intestines and nourishing the blood for beauty. Pig's blood is rich in vitamin B2, vitamin C, protein, iron, phosphorus, calcium, nikonic acid and other nutrients." ,
            "Sweet in taste and warm in nature, mutton has the effect of tonifying deficient labour, dispelling cold, warming the qi and blood; benefiting kidney qi, replenishing the weakened form and opening the stomach and strengthening the strength. It is used for all deficiency symptoms such as lack of qi and blood, cold pain in the abdomen, weakness and fear of cold, soreness in the waist and knees, yellowing of the face and muscle, deficiency of blood and qi, etc. It is most suitable for winter consumption." ,
            "Beef has the effect of nourishing the middle of the body, nourishing the spleen and stomach, strengthening the muscles and bones, resolving phlegm and relieving wind, and stopping thirst and salivation. It is rich in protein and less fat, and its amino acid composition is closer to the body's needs than that of pork, which can improve the body's ability to resist disease." ,
            "Nourishes the blood, tonifies the liver and brightens the eyes. Beef liver is rich in iron and is the most commonly used food to nourish the blood."
            , "Goose meat contains a variety of amino acids necessary for human growth and development, and its composition is close to the ratio of amino acids required by the human body. From the biological value, goose meat is ideal high protein, low fat, low cholesterol nutritional health food, and its linolenic acid content exceeds that of other meats. Goose meat is flat and sweet in nature, and has the effect of benefiting qi, tonic deficiency, and stomach to quench thirst." ,
            "Rabbit meat is tender, tasty and nutritious. Compared with pork, beef and lamb, rabbit meat has a unique nutritional composition with high iron, high calcium, high phospholipids and low fat and cholesterol, and has a high digestibility (up to 85%), which is easily digested and absorbed after eating." ,
            "Dog meat warms the spleen and stomach, tonifies the kidneys and helps the yang, strengthens the strength and nourishes the blood. Dog meat not only has a high protein content, but also has excellent protein quality, especially a large proportion of globulin, which has a significant effect on enhancing the body's resistance to disease and cellular vitality and organ function." ,
            "Rich in protein, fat, iron, potassium, sugar and many other nutrients. The content of saturated fatty acids is much less than that of pork, beef and lamb, and the low melting point of fatty acids makes it easy to digest. The cholesterol contained is somewhat lower than that of fish. It is also a good idea to take a look at the, which is the main tonic for deficient labor, the most antiseptic heat, the urine, the edema, the fullness, the internal organs, the sores, and the epilepsy." ,
            "Chicken meat is rich in protein, many kinds, its content is higher than that of pork, beef and mutton, while the fat content is lower than other meats, and mostly unsaturated fatty acids, high nutritional value, high digestibility, easily absorbed and utilized by the body, has the effect of enhancing physical strength and strengthening the body." ,
            "Donkey meat is rich in protein and contains animal gum, collagen, calcium and sulphur, etc. It contains advanced unsaturated fatty acids, especially linoleic acid and linolenic acid, which have good health effects on arteriosclerosis, coronary heart disease and hypertension. According to Chinese medicine: donkey meat is sweet and cool in nature, and has the effect of nourishing the qi and blood, nourishing the yin and strengthening the yang, and calming the mind and dispelling annoyance." ,
            "Eggs are considered to be a nutritious food, containing protein, fat, lecithin, lecithin, vitamins and minerals needed by the body such as iron, calcium and potassium. Eggs are sweet in taste and calm in nature, and have the effect of nourishing the heart and calming the mind, tonifying the blood, and nourishing the yin and moistening dryness." ,
            "Carp has a high protein content and is of good quality, with a digestibility and absorption rate of 96%, and supplies essential amino acids, minerals, vitamin A and vitamin D. Carp is sweet in taste and flat in nature, and has the effect of strengthening the spleen and appetite, reducing oedema and facilitating urination." ,
            "Yellow fish is rich in protein, trace elements and vitamins, and has a very beneficial effect on the human body. According to Chinese medicine, yellow fish has the effect of harmonizing the stomach and stopping bleeding, benefiting the kidneys, tonifying the deficiencies, strengthening the spleen and appetite, calming the mind and stopping dysentery, benefiting the qi and filling the essence, which is good for anemia, insomnia, dizziness, loss of appetite and women's postpartum deficiency." ,
            "Shrimp is extremely nutritious, containing several times to dozens of times more protein than fish, eggs and milk; it is also rich in minerals such as potassium, iodine, magnesium, phosphorus and vitamin A, aminophylline and other components." ,
            "Shrimp skin is rich in protein and minerals, especially calcium, which is one of the better ways to supplement calcium for those who lack it." ,
            "Crab is rich in protein and trace elements such as calcium, phosphorus and iron, and is a good tonic for the body. In Chinese medicine, crab is considered to be cold and salty in nature, and has the function of clearing heat, dispersing blood knots, renewing injuries, regulating the meridians and nourishing yin; its shell can clear heat and detoxify the body, break up blockages and clear up pain." ,
            "The meat is tasty and nutritious, with a high protein content and a reasonable composition of amino acid types and ratios; the fat content is low and the unsaturated fatty acids are high and easily digested and absorbed by the body. The clam is salty and cold in nature, and has the effect of nourishing the yin, promoting water retention, resolving phlegm and softening hardness." ,
            "Turtle meat is favoured to nourish the Yin, tonify the kidneys and tonic the deficiencies. It is a good food for people who have been sick for a long time and are weak. The turtle is salty in taste and calm in nature, with the effects of nourishing Yin and cooling the blood, calming the liver and benefiting Qi, dispersing nodules and softening hardness, and eliminating siltation." ,
            "Contains protein, fat, carbohydrates, calcium, phosphorus, iron, thiamin, riboflavin, nikonic acid and vitamins. The snail is salty and cold in nature, and has the effect of clearing heat and water, removing dampness and relieving reading." ,
            "Garlic is rich in nutrients, especially sulphur-containing compounds such as allicin and functional components such as selenoproteins, which are uniquely bioactive, and these substances have significant medical and food values. According to Chinese medical theory, garlic is pungent and hot in raw form and sweet and warm in cooked form, and can remove cold and dampness, frighten yin and evil, lower the qi and warm the middle, eliminate grain and stagnation, break up bad blood and attack cold stagnation." ,
            "The main nutrients of spring onions are protein, sugar, vitamin A original (mainly contained in the green onion leaves), dietary fibre and minerals such as phosphorus, iron and magnesium. According to Chinese medicine, green onion can sweat and relieve symptoms, disperse cold and Yang, and detoxify and disperse condensation. It is used to treat mild wind-cold colds, carbuncles, sores and poison, dysentery with weak veins, abdominal pain from cold condensation, and unfavourable urination." ,
            "Radish is rich in carbohydrates, fiber, vitamin C and minerals such as calcium, phosphorus, potassium, iron, etc. It is nutritious; radish also has the effect of promoting metabolism and appetite, and is effective in treating indigestion, bloating and fullness in the stomach, coughing and phlegm, chest congestion and asthma, and wind and cold." ,
            "Celery contains protein, fat, carbohydrates, fibre, vitamins, minerals and other nutrients. Among them, the content of vitamin B and P is high. The content of mineral elements calcium, phosphorus and iron is even higher than that of ordinary green vegetables." ,
            "Leek is warm in nature, pungent in taste, has a tonic effect on the kidneys and Yang, and contains a lot of vitamins and crude fibre, which can improve gastrointestinal motility and treat constipation." ,
            "Spinach is high in water (90% to 93%) and low in calories, and is a good source of magnesium, iron, potassium and vitamin A; it is also a superior source of calcium and vitamin C. It is also richer in phosphorus, zinc, folic acid and vitamin B6. The vitamin C contained in spinach enhances the absorption of iron and promotes the joint action of iron and blood-building folic acid, effectively preventing anaemia." ,
            "Lettuce contains protein, lipids, carbohydrates, calcium, phosphorus, iron, carotenoids, and vitamin B, vitamin C, and especially niacin. Lettuce has a fresh and slightly bitter taste, which stimulates the secretion of digestive enzymes and increases appetite. Its milky pulp enhances the secretion of gastric juice, digestive glands and bile, thus promoting digestion." ,
            "It has the effects of nourishing Yin and cooling the blood, harmonizing the middle and moistening the intestines, clearing heat and phlegm, quenching thirst and relieving irritation, clearing heat and benefiting the qi, benefiting the stomach by clearing away the heat, diuretic and laxative, detoxifying and penetrating the rash, nourishing the liver and brightening the eyes, and eliminating food, as well as opening the stomach and strengthening the spleen, widening the intestines and diaphragm, opening the diaphragm and expelling phlegm, eliminating grease and detoxifying wine." ,
            "Tomatoes are rich in vitamin C. The lycopene they contain has a diuretic and inhibits bacterial growth, and is an excellent antioxidant. Tomatoes are slightly sweet and sour, and are flat in nature, with the effect of quenching thirst, strengthening the stomach and eliminating food, cooling the blood and calming the liver, clearing heat and detoxifying the body, and lowering blood pressure." ,
            "Selenium, a trace element contained in onions, is a strong antioxidant that scavenges free radicals in the body, enhances cellular vitality and metabolism, and has anti-ageing effects." ,
            "Vinegar can appease the appetite, promote the secretion of saliva and gastric juice, help digestion and absorption, make the appetite strong and eliminate food stagnation. Vinegar also has a good anti-bacterial and antiseptic effect, which can effectively prevent intestinal diseases, influenza and respiratory diseases." ,
            "Tea contains about 500 compounds, some of which are essential nutrients for the human body. Such as vitamins, proteins, amino acids, lipids, sugars and mineral elements, which have high nutritional value to the human body. Some are components that have health and medicinal value to the human body, such as tea polyphenols, caffeine, lipopolysaccharides, etc., and regular consumption of tea is beneficial to human health." ,
            "Soy milk is rich in vegetable protein, phospholipids, vitamins B1 and B2, niacin and minerals such as iron and calcium, especially calcium content, although not as high as tofu, but richer than any other dairy. Soy milk is an ideal food for the prevention and treatment of diseases such as hyperlipidemia, hypertension and atherosclerosis." ,
            "The glucose, fructose and other monosaccharide and polysaccharide energy substances contained in brown sugar provide energy for the body." ,
            "Honey contains more than 70% converted sugar, which can be absorbed and used directly by the cells of the body's intestinal wall, without the need to be digested by the body, and can aid digestion when taken regularly." ,
            "Milk is highly nutritious and it contains a wide range of minerals, in addition to calcium as we know it, phosphorus, iron, zinc, copper, manganese and molybdenum. Milk is the best source of calcium for the body and has a very good calcium to phosphorus ratio, which facilitates the absorption of calcium. Milk contains 20 amino acids that make up human proteins, 8 of which are essential amino acids that the body cannot synthesize on its own." ,
            "White wine is mainly composed of aqueous ethanol and a few trace elements. A small amount of white wine taken at night can calmly promote blood circulation and play a hypnotic role. Drinking small amounts of white wine stimulates gastric and salivary secretions, thus strengthening the stomach and relieving pain, facilitating urination and driving away worms. White wine is bitter, sweet and pungent, warm in nature, toxic and enters the heart, liver, lung and stomach meridians." ,
            "Beer is rich in nutrients and is rich in amino acids, which are produced by the enzymatic breakdown of the proteins contained in the raw material barley. It also contains many vitamins. Drinking beer in moderation has the effect of strengthening the heart, diuretic and stomach."};



        public static List<FoodBean> getAllFoodList(){
            List<FoodBean>list = new ArrayList<>();
            for (int i = 0; i < food.length; i++) {
                FoodBean bean = new FoodBean(food[i],food1[i],fooddesc[i],resId[i]);
                list.add(bean);
            }
            return list;

    }


}
