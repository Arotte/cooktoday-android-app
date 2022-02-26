/*
    Singleton class holding a list of
    recipe image web URLs.

    FOR DEBUGGING/DEVELOPMENT PURPOSES ONLY!

    Usage:
    SampleRecipeImgURLs.i().get(i)
 */

package com.aron.cooktoday.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SampleRecipeImgURLs {

    private static SampleRecipeImgURLs instance = null;

    private List<String> URLs;

    private SampleRecipeImgURLs() {
        URLs = new ArrayList<>(Arrays.asList(
                /* ADD NEW URLs HERE */
                "https://www.averiecooks.com/wp-content/uploads/2021/01/garlicbutterchicken-5.jpg",
                "https://static01.nyt.com/images/2021/03/28/dining/mc-shakshuka/mc-shakshuka-articleLarge.jpg",
                "https://www.thespruceeats.com/thmb/cO72JFFH0TCAufENSxUfqE8TmKw=/450x0/filters:no_upscale():max_bytes(150000):strip_icc()/vegan-tofu-tikka-masala-recipe-3378484-hero-01-d676687a7b0a4640a55be669cba73095.jpg",
                "https://static01.nyt.com/images/2021/06/01/dining/11lightveg-roundup-8/11lightveg-roundup-8-articleLarge-v2.jpg?quality=75&auto=webp&disable=upscale",
                "https://www.tasteofhome.com/wp-content/uploads/2018/01/Meatball-Alphabet-Soup_EXPS_SSMZ20_22243_E10_10_2b.jpg?resize=768,768"
        ));
    }

    public static SampleRecipeImgURLs i() {
        if (instance == null)
            instance = new SampleRecipeImgURLs();
        return instance;
    }

    // =======================================================

    public int n() {
        return URLs.size();
    }

    public String get(int position) {
        if (position < 0 || position > URLs.size() - 1)
            return null;
        return URLs.get(position);
    }
}
