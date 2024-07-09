package com.jk.category

import com.jk.category_data.TransactionCategory


fun TransactionCategory.toUI(): CategoryUI {
return CategoryUI(id=id,name=name,color=color,isExpenses=isExpenses)
}