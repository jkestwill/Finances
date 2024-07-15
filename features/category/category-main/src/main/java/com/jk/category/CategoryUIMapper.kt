package com.jk.category

import com.jk.category_common_data.TransactionCategory
import com.jk.category_common_ui.CategoryUI


fun TransactionCategory.toUI(): CategoryUI {
return CategoryUI(id=id,name=name,color=color,isExpenses=isExpenses)
}