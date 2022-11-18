package com.globits.mita.ui.pacs.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.globits.mita.R
import com.globits.mita.data.model.Document
import com.globits.mita.ui.theme.BACKGROUND_IMAGE
import com.globits.mita.ui.theme.BODER_ICON
import com.globits.mita.ui.theme.ICON_IMAGE
import com.globits.mita.ui.theme.TEXT_IMAGE
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Preview
@Composable
fun DefaultViewImage() {
    ImageScreen(document = Document())
    {}
}


@OptIn(ExperimentalPagerApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ImageScreen(document: Document, onBack: () -> Unit) {

    val pagerState = rememberPagerState(
        pageCount = document.images?.size ?: 0,
    )

    var scale by remember { mutableStateOf(1f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = BACKGROUND_IMAGE),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Hình ảnh ${pagerState.currentPage + 1}/${document.images?.size}",
            fontSize = 14.sp,
            color = TEXT_IMAGE,
            modifier = Modifier.padding(bottom = 5.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            GlideImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(start = 20.dp, end = 20.dp)
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale
                    ),
                model =
                when (page) {
                    page -> "http://192.168.0.157:8020/mita/public/images/${document.images?.get(page)}"

                    else -> throw IllegalStateException("image not provided for page $page")
                }, contentDescription = null
            )
            {
                it.thumbnail()
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            AnimatedVisibility(
                visible = pagerState.currentPage != 0,
                enter = fadeIn(
                    initialAlpha = 0.4f
                ),
                exit = fadeOut(
                    animationSpec = tween(durationMillis = 250)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            GlobalScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage - 1)
                            }
                        },
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_back),
                        contentDescription = ""
                    )
                    Text(
                        text = "Trước",
                        fontSize = 14.sp,
                        color = TEXT_IMAGE,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }


            AnimatedVisibility(
                visible = pagerState.currentPage + 1 != document.images?.size,
                enter = fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f
                ),
                exit = fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 250)
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            GlobalScope.launch {
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            }
                        },
                    horizontalArrangement = Arrangement.End,
                ) {

                    Text(
                        text = "Sau",
                        fontSize = 14.sp,
                        color = TEXT_IMAGE,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.img_next),
                        contentDescription = ""
                    )
                }
            }

        }

        Column(
            modifier = Modifier
                .height(43.dp)
                .width(43.dp)
                .border(width = 1.dp, color = BODER_ICON, RoundedCornerShape(21.dp))
                .clip(shape = RoundedCornerShape(21.dp))
                .background(color = ICON_IMAGE)
                .clickable {
                    onBack()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = R.drawable.img_close), contentDescription = "")
        }


    }
}

