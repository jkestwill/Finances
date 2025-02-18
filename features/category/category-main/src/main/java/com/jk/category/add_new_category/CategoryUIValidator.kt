package com.jk.category.add_new_category

import com.jk.category_common_ui.CategoryUI
import com.jk.common_data.FinanceHelperException
import com.jk.common_data.Validator

class CategoryUIValidator: Validator<CategoryUI> {
    override fun validate(target: CategoryUI) {
        when {
            target.name.isEmpty() -> {
              throw FinanceHelperException(scope = "categoryUI_validator", message =  "Name must not be empty")
            }
        }
    }
}