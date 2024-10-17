package com.shoe_store.config;

import com.shoe_store.services.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServicesConfig {

    private AdminService adminService;

    private CommentaryService commentaryService;

    private IngredientService ingredientService;

    private RecipeService recipeService;

    private RatingService ratingService;

    private SectionService sectionService;

    private StepService stepService;

    private UserService userService;

    public static ServicesConfigBuilder builder() {
        return new ServicesConfigBuilder();
    }

    public static class ServicesConfigBuilder {

        private AdminService adminService;

        private CommentaryService commentaryService;

        private IngredientService ingredientService;

        private RecipeService recipeService;

        private RatingService ratingService;

        private SectionService sectionService;

        private StepService stepService;

        private UserService userService;

        private ServicesConfigBuilder() {
        }

        public ServicesConfigBuilder adminService(AdminService adminService) {
            this.adminService = adminService;
            return this;
        }

        public ServicesConfigBuilder commentaryService(CommentaryService commentaryService) {
            this.commentaryService = commentaryService;
            return this;
        }

        public ServicesConfigBuilder ingredientService(IngredientService ingredientService) {
            this.ingredientService = ingredientService;
            return this;
        }

        public ServicesConfigBuilder recipeService(RecipeService recipeService) {
            this.recipeService = recipeService;
            return this;
        }

        public ServicesConfigBuilder ratingService(RatingService ratingService) {
            this.ratingService = ratingService;
            return this;
        }

        public ServicesConfigBuilder sectionService(SectionService sectionService) {
            this.sectionService = sectionService;
            return this;
        }

        public ServicesConfigBuilder stepService(StepService stepService) {
            this.stepService = stepService;
            return this;
        }

        public ServicesConfigBuilder userService(UserService userService) {
            this.userService = userService;
            return this;
        }

        public ServicesConfig build() {
            return new ServicesConfig(this.adminService, this.commentaryService, this.ingredientService, this.recipeService, this.ratingService, this.sectionService, this.stepService, this.userService);
        }

        public String toString() {
            return "ServicesConfig.ServicesConfigBuilder(adminService=" + this.adminService + ", commentaryService=" + this.commentaryService + ", ingredientService=" + this.ingredientService + ", recipeService=" + this.recipeService + ", ratingService=" + this.ratingService + ", sectionService=" + this.sectionService + ", stepService=" + this.stepService + ", userService=" + this.userService + ")";
        }

    }

}
