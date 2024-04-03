package com.jk.financehelper.ui.custom


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout


//@Composable
//fun FixedGrid(columns:Int,data:@Composable (Int)->Unit ){
//    Layout(modifier = Modifier, content= data ){meausurable,constraints->
//        val placeable =  meausurable.map {
//            it.measure(constraints)
//        }
//
//        layout(width =constraints.maxWidth , height =constraints.maxHeight ){
//            var y=0
//            var x=0
//            placeable.forEachIndexed {index,pl->
//                pl.placeRelative(x,y)
//                if(index%columns==0){
//                    y+=pl.height+10
//                    x=0
//                } else
//                    x+=pl.width
//            }
//        }
//    }
//}
@Composable
fun VerticalGrid(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val itemWidth = constraints.maxWidth / columns
        // Keep given height constraints, but set an exact width
        val itemConstraints = constraints.copy(
            minWidth = itemWidth,
            maxWidth = itemWidth
        )
        // Measure each item with these constraints
        val placeables = measurables.map { it.measure(itemConstraints) }
        // Track each columns height so we can calculate the overall height
        val columnHeights = Array(columns) { 0 }
        placeables.forEachIndexed { index, placeable ->
            val column = index % columns
            columnHeights[column] += placeable.height
        }
        val height = (columnHeights.maxOrNull() ?: constraints.minHeight)
            .coerceAtMost(constraints.maxHeight)
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            // Track the Y co-ord per column we have placed up to
            val columnY = Array(columns) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val column = index % columns
                placeable.placeRelative(
                    x = column * itemWidth,
                    y = columnY[column]
                )
                columnY[column] += placeable.height
            }
        }
    }
}