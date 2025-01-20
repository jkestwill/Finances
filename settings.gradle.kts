pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }

}

rootProject.name = "FinanceHelper"
include(":app")
include(":core:transaction-database")
include(":core:money:currencyExchangeApi")
include(":core:money:exchange-rate-data")
include(":core:category:category-data")
include(":core:transaction:transaction-data")
include(":core:goods:goods-data")
include(":core:money:money-data")
include(":core:settings")
include(":features:category:category-main")
include(":features:goods:goods")
include(":features:currency:currency")
include(":features:transaction")
include(":features:currency:currency-exchange")
include(":common:common-utils")
include(":common:common-data:category")
include(":common:common-data:transaction")
include(":common:common-utils-ui")
include(":common:common-data:goods")
include(":common:common-data:money")
include(":common:common-ui:transaction")
include(":common:common-ui:goods")
include(":common:common-ui:category")
include(":common:common-ui:money")
include(":common:shared_res")

