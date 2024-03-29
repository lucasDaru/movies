package com.texoit.movie.fixture;

import com.texoit.movie.entity.Studio;

public class StudioFixture {

    public static Studio createStudio(String name) {
        Studio studio = new Studio();
        studio.setName(name);
        return studio;
    }

}
