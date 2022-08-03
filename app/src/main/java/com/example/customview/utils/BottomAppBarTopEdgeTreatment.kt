package com.example.customview.utils

import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath
import kotlin.math.abs

class BottomAppBarTopEdgeTreatment(
    val fabDiameter :Float= 0f,
    val fabMargin: Float = 0f,
    val roundedCornerRadius: Float = 0f,
    val cradleVerticalOffset: Float = 0f
) : EdgeTreatment(), Cloneable {


    private val ARC_QUARTER = 90
    private val ARC_HALF = 180
    private val ANGLE_UP = 270
    private val ANGLE_LEFT = 180
    private val ROUNDED_CORNER_FAB_OFFSET = 1.75f

    private val horizontalOffset = 0f
    private val fabCornerSize = -1f

    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath
    ) {
        if (fabDiameter == 0f) {
            // There is no cutout to draw.
            shapePath.lineTo(length, 0f)
            return
        }

        val cradleDiameter = fabMargin * 2 + fabDiameter
        val cradleRadius = cradleDiameter / 2f
        val roundedCornerOffset = interpolation * roundedCornerRadius
        val middle = center + horizontalOffset

        // The center offset of the cutout tweens between the vertical offset when attached, and the
        // cradleRadius as it becomes detached.

        // The center offset of the cutout tweens between the vertical offset when attached, and the
        // cradleRadius as it becomes detached.
        var verticalOffset =
            interpolation * cradleVerticalOffset + (1 - interpolation) * cradleRadius
        val verticalOffsetRatio = verticalOffset / cradleRadius
        if (verticalOffsetRatio >= 1.0f) {
            // Vertical offset is so high that there's no curve to draw in the edge, i.e., the fab is
            // actually above the edge so just draw a straight line.
            shapePath.lineTo(length, 0f)
            return  // Early exit.
        }

        // Calculate the path of the cutout by calculating the location of two adjacent circles. One
        // circle is for the rounded corner. If the rounded corner circle radius is 0 the corner will
        // not be rounded. The other circle is the cutout.

        // Calculate the X distance between the center of the two adjacent circles using pythagorean
        // theorem.

        // Calculate the path of the cutout by calculating the location of two adjacent circles. One
        // circle is for the rounded corner. If the rounded corner circle radius is 0 the corner will
        // not be rounded. The other circle is the cutout.

        // Calculate the X distance between the center of the two adjacent circles using pythagorean
        // theorem.
        val cornerSize = fabCornerSize * interpolation
        val useCircleCutout = fabCornerSize == -1f || abs(fabCornerSize * 2f - fabDiameter) < .1f
        var arcOffset = 0f
        if (!useCircleCutout) {
            verticalOffset = 0f
            arcOffset = ROUNDED_CORNER_FAB_OFFSET
        }

        val distanceBetweenCenters = cradleRadius + roundedCornerOffset
        val distanceBetweenCentersSquared = distanceBetweenCenters * distanceBetweenCenters
        val distanceY = verticalOffset + roundedCornerOffset
        val distanceX =
            Math.sqrt((distanceBetweenCentersSquared - distanceY * distanceY).toDouble()).toFloat()

        // Calculate the x position of the rounded corner circles.

        // Calculate the x position of the rounded corner circles.
        val leftRoundedCornerCircleX = middle - distanceX
        val rightRoundedCornerCircleX = middle + distanceX

        // Calculate the arc between the center of the two circles.

        // Calculate the arc between the center of the two circles.
        val cornerRadiusArcLength =
            Math.toDegrees(Math.atan((distanceX / distanceY).toDouble())).toFloat()
        val cutoutArcOffset = ARC_QUARTER - cornerRadiusArcLength + arcOffset

        // Draw the starting line up to the left rounded corner.

        // Draw the starting line up to the left rounded corner.
        shapePath.lineTo( /* x= */leftRoundedCornerCircleX,  /* y= */0f)

        // Draw the arc for the left rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(leftRoundedCornerCircleX, roundedCornerOffset)`.

        // Draw the arc for the left rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(leftRoundedCornerCircleX, roundedCornerOffset)`.
        shapePath.addArc( /* left= */
            leftRoundedCornerCircleX - roundedCornerOffset,  /* top= */
            0f,  /* right= */
            leftRoundedCornerCircleX + roundedCornerOffset,  /* bottom= */
            roundedCornerOffset * 2,  /* startAngle= */
            ANGLE_UP.toFloat(),  /* sweepAngle= */
            cornerRadiusArcLength
        )

        if (useCircleCutout) {
            // Draw the cutout circle.
            shapePath.addArc( /* left= */
                middle - cradleRadius,  /* top= */
                -cradleRadius - verticalOffset,  /* right= */
                middle + cradleRadius,  /* bottom= */
                cradleRadius - verticalOffset,  /* startAngle= */
                ANGLE_LEFT - cutoutArcOffset,  /* sweepAngle= */
                cutoutArcOffset * 2 - ARC_HALF
            )
        } else {
            val cutoutDiameter = fabMargin + cornerSize * 2f
            shapePath.addArc( /* left= */
                middle - cradleRadius,  /* top= */
                -(cornerSize + fabMargin),  /* right= */
                middle - cradleRadius + cutoutDiameter,  /* bottom= */
                fabMargin + cornerSize,  /* startAngle= */
                ANGLE_LEFT - cutoutArcOffset,  /* sweepAngle= */
                (cutoutArcOffset * 2 - ARC_HALF) / 2f
            )
            shapePath.lineTo(
                middle + cradleRadius - (cornerSize + fabMargin / 2f),  /* y= */
                cornerSize + fabMargin
            )
            shapePath.addArc( /* left= */
                middle + cradleRadius - (cornerSize * 2f + fabMargin),  /* top= */
                -(cornerSize + fabMargin),  /* right= */
                middle + cradleRadius,  /* bottom= */
                fabMargin + cornerSize,  /* startAngle= */
                90f,  /* sweepAngle= */
                -90 + cutoutArcOffset
            )
        }

        // Draw an arc for the right rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(rightRoundedCornerCircleX, roundedCornerOffset)`.

        // Draw an arc for the right rounded corner circle. The bounding box is the area around the
        // circle's center which is at `(rightRoundedCornerCircleX, roundedCornerOffset)`.
        shapePath.addArc( /* left= */
            rightRoundedCornerCircleX - roundedCornerOffset,  /* top= */
            0f,  /* right= */
            rightRoundedCornerCircleX + roundedCornerOffset,  /* bottom= */
            roundedCornerOffset * 2,  /* startAngle= */
            ANGLE_UP - cornerRadiusArcLength,  /* sweepAngle= */
            cornerRadiusArcLength
        )

        // Draw the ending line after the right rounded corner.

        // Draw the ending line after the right rounded corner.
        shapePath.lineTo( /* x= */length,  /* y= */0f)
    }
}