package com.emirhankarci.tutorly.presentation.ui.screen

import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.TutorlyCardItem
import com.emirhankarci.tutorly.domain.entity.TutorlyCardItemResult
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToGradeSelection: () -> Unit = {}
) {
    HomeScreenContent(
        modifier = modifier,
        onNavigateToGradeSelection = onNavigateToGradeSelection
    )
}


@Composable
fun HomeScreenContent(
    modifier: Modifier,
    onNavigateToGradeSelection: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            TutorlyCard(
                borderColor = Color.Blue,
                textColor = Color.Black,
                icon = Icons.Default.Face,
                title = "Derslere Git",
                description = "Sınıf seçimi yaparak derslere başla",
                cardHeight = 150.dp,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp),
                onClick = { onNavigateToGradeSelection() }
            )
        }

        item { Spacer(Modifier.height(4.dp)) }


        item {
            Text(
                "Quick Actions",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Start
            )
        }

        item { Spacer(Modifier.height(12.dp)) }


        items(TutorlyCardItemResult.chunked(2)) { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                rowItems.forEach { item ->
                    Box(modifier = Modifier.weight(1f)) {
                        TutorlyCard(
                            borderColor = Color.Blue,
                            textColor = Color.Black,
                            icon = item.icon,
                            title = item.title,
                            cardHeight = 130.dp,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }
                }

                repeat(2 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        item { Spacer(Modifier.height(8.dp)) }


        item {
            Row(Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    "Study Plan",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Start
                )
                Text(
                    "View All",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                )
            }

        }

        item { Spacer(Modifier.height(8.dp)) }


        item {
            TutorlyCard(
                borderColor = Color.Blue,
                textColor = Color.Black,
                icon = Icons.Default.DateRange,
                title = "Study Plan",
                description = "Study plan buraya",
                cardHeight = 150.dp,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}



@Composable
fun TutorlyCard(
    borderColor: Color,
    textColor: Color,
    icon: ImageVector,
    title: String,
    description: String? = "",
    cardWidth: Dp? = null,  // opsiyonel
    cardHeight: Dp,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = if (cardWidth != null) {
            modifier
                .width(cardWidth)
                .height(cardHeight)
                .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
        } else {
            modifier
                .fillMaxWidth()
                .height(cardHeight)
                .border(1.5.dp, borderColor, RoundedCornerShape(14.dp))
        },
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "cardicon",
                tint = textColor,
                modifier = Modifier.size(70.dp)
            )
            Text(
                title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
            )
            Text(
                description.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = textColor,
            )
        }
    }
}

@Preview
@Composable
private fun TutorlyCardPreview() {
    TutorlyCard( borderColor = Color.Blue,
        textColor = Color.Blue,
        icon = Icons.Default.ShoppingCart,
        title = "Konu",
        description = "Matematik",
        120.dp,120.dp

        )

}


@Preview
@Composable
private fun HomeScreenPrev() {
    HomeScreen(modifier = Modifier.fillMaxSize())
    
}
