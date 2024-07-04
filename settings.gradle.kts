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
include(":core:money:currencyExchangeApiBy")
include(":core:money:exchange-rate-data")
include(":core:money:currency-exchange")
include(":core:category:category-data")
include(":core:common-data")
include(":features:category:category-main")
include(":core:transaction:transaction-data")
include(":core:goods:goods-data")

include(":core:money:money-data")
include(":features:goods:goods")
include(":features:common")
