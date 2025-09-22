package com.emirhankarci.tutorly.presentation.ui.screen

import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emirhankarci.tutorly.domain.entity.TutorlyCardItem
import com.emirhankarci.tutorly.domain.entity.TutorlyCardItemResult

@Composable
fun HomeScreen() {


}

@Composable
fun HomeScreenContent() {

    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),


    ){
        TutorlyCard(
            borderColor = Color.Blue,
            textColor = Color.Black,
            icon = Icons.Default.Face,
            title = "Ask AI Tutor",
            description = "Get instant help with any topic",
            cardWidth = 350.dp,
            cardHeight = 150.dp
        )
        Spacer(Modifier.height(20.dp))
        Text("Quick Actions",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Start)

        Spacer(Modifier.height(20.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(TutorlyCardItemResult.size) { index ->
                val item = TutorlyCardItemResult[index]
                TutorlyCard(
                    borderColor = Color.Blue,
                    textColor = Color.Black,
                    icon = item.icon,
                    title = item.title,
                    cardWidth = 150.dp,
                    cardHeight = 130.dp
                )
            }
        }

        Spacer(Modifier.height(20.dp))
        Text("Study Plan",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Start)

        Spacer(Modifier.height(22.dp))

    }



}


@Composable
fun TutorlyCard(
    borderColor: Color,
    textColor: Color,
    icon: ImageVector,
    title: String,
    description: String ?="",
    cardWidth: Dp,
    cardHeight: Dp

) {
    Card (
        modifier = Modifier
            .height(cardHeight)
            .width(cardWidth)
            .border(
                1.5.dp, borderColor,
                RoundedCornerShape(14.dp)
            ),
        shape = RoundedCornerShape(14.dp)
    ){
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
            Text(title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor,
                )
            Text(description.toString(),
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
        title = "Konu",// Compose’un sabit ikonu
        description = "Matematik",
        120.dp,120.dp

        )

}

@Preview
@Composable
private fun HomeScreenContentPrev() {
    HomeScreenContent()

}