package com.emirhankarci.tutorly.domain.util

import androidx.compose.ui.graphics.Color
import com.emirhankarci.tutorly.domain.entity.ScheduleItem
import kotlin.random.Random

object LessonPlanParser {

    private val subjectColors = listOf(
        Color(0xFF2196F3), // Blue
        Color(0xFFF44336), // Red
        Color(0xFF4CAF50), // Green
        Color(0xFF9C27B0), // Purple
        Color(0xFFFF9800), // Orange
        Color(0xFF00BCD4), // Cyan
        Color(0xFFE91E63), // Pink
        Color(0xFF3F51B5), // Indigo
        Color(0xFF8BC34A), // Light Green
        Color(0xFFFF5722)  // Deep Orange
    )

    private val daysOfWeek = listOf(
        "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar"
    )

    fun parseAILessonPlan(aiResponse: String): List<ScheduleItem> {
        val scheduleItems = mutableListOf<ScheduleItem>()

        try {
            // Normalize the text first
            val normalizedText = aiResponse.lowercase()
            val lines = aiResponse.split("\n").map { it.trim() }.filter { it.isNotEmpty() }

            var currentDay = ""
            var colorIndex = 0

            // Strategy 1: Line by line parsing
            for (line in lines) {
                // Check for day mentions
                val foundDay = daysOfWeek.find { day ->
                    line.contains(day, ignoreCase = true) ||
                    line.contains("${day.take(3)}", ignoreCase = true)
                }
                if (foundDay != null) {
                    currentDay = foundDay
                }

                // Parse lesson from line
                val lesson = parseFlexibleLine(line, currentDay, subjectColors[colorIndex % subjectColors.size])
                lesson?.let {
                    scheduleItems.add(it)
                    colorIndex++
                }
            }

            // Strategy 2: Pattern-based extraction if line parsing failed
            if (scheduleItems.isEmpty()) {
                scheduleItems.addAll(extractWithPatterns(aiResponse))
            }

            // Strategy 3: Smart subject extraction if nothing found
            if (scheduleItems.isEmpty()) {
                scheduleItems.addAll(createSmartScheduleFromText(aiResponse))
            }

            // Strategy 4: Last resort - simple subjects
            if (scheduleItems.isEmpty()) {
                scheduleItems.addAll(extractSimpleSubjects(aiResponse))
            }

        } catch (e: Exception) {
            // If all parsing fails, create a basic fallback
            return createFallbackSchedule(aiResponse)
        }

        return scheduleItems.ifEmpty { createFallbackSchedule(aiResponse) }
    }

    private fun parseFlexibleLine(line: String, day: String, color: Color): ScheduleItem? {
        if (day.isEmpty()) return null

        try {
            val lowerLine = line.lowercase()

            // Skip lines that are just day names or headers
            if (daysOfWeek.any { lowerLine.contains(it.lowercase()) } && !lowerLine.contains(":") && !lowerLine.contains("saat")) {
                return null
            }

            // Extract subject - look for common patterns
            var subject = ""
            val subjectPatterns = listOf(
                Regex("([a-züşğıçö]+(?:\\s+[a-züşğıçö]+)*?)\\s*[-:]?\\s*\\d"),
                Regex("([a-züşğıçö]+(?:\\s+[a-züşğıçö]+)*?)\\s+dersi"),
                Regex("([a-züşğıçö]+(?:\\s+[a-züşğıçö]+)*?)\\s+(?:saat|saatinde)"),
                Regex("([a-züşğıçö]+)(?:\\s+[-:])")
            )

            for (pattern in subjectPatterns) {
                val match = pattern.find(lowerLine)
                if (match != null) {
                    subject = match.groupValues[1].trim().replaceFirstChar { it.uppercase() }
                    break
                }
            }

            // If no subject found, try to extract first meaningful word
            if (subject.isEmpty()) {
                val words = lowerLine.split(Regex("[\\s\\-.:,;]+")).filter { it.length > 2 }
                subject = words.firstOrNull { word ->
                    !word.matches(Regex("\\d+")) &&
                    !listOf("saat", "dakika", "dk", "ders", "için", "ile", "olan", "var", "yok").contains(word)
                }?.replaceFirstChar { it.uppercase() } ?: "Ders"
            }

            // Extract time
            var time = "09:00"
            val timePatterns = listOf(
                Regex("(\\d{1,2})[:.](\\d{2})"),
                Regex("(\\d{1,2})\\s*(?:saat|saatinde)"),
                Regex("saat\\s*(\\d{1,2})")
            )

            for (pattern in timePatterns) {
                val match = pattern.find(line)
                if (match != null) {
                    val hour = match.groupValues[1].padStart(2, '0')
                    val minute = if (match.groupValues.size > 2 && match.groupValues[2].isNotEmpty()) {
                        match.groupValues[2]
                    } else "00"
                    time = "$hour:$minute"
                    break
                }
            }

            // Extract duration
            var duration = "60 dk"
            val durationPatterns = listOf(
                Regex("(\\d+)\\s*(?:dk|dakika)"),
                Regex("(\\d+)\\s*saat"),
                Regex("(\\d+\\.\\d+)\\s*saat")
            )

            for (pattern in durationPatterns) {
                val match = pattern.find(lowerLine)
                if (match != null) {
                    val value = match.groupValues[1].toFloatOrNull() ?: 1f
                    duration = if (lowerLine.contains("saat")) {
                        "${(value * 60).toInt()} dk"
                    } else {
                        "${value.toInt()} dk"
                    }
                    break
                }
            }

            return ScheduleItem(
                subject = subject,
                time = time,
                duration = duration,
                color = color,
                day = day,
                notes = "AI tarafından oluşturuldu"
            )

        } catch (e: Exception) {
            return null
        }
    }

    private fun extractWithPatterns(text: String): List<ScheduleItem> {
        val items = mutableListOf<ScheduleItem>()
        var colorIndex = 0

        // Pattern 1: "Matematik 9:00-10:30" style
        val pattern1 = Regex("([a-züşğıçö]+(?:\\s+[a-züşğıçö]+)*)\\s+(\\d{1,2})[:.](\\d{2})(?:[-–](\\d{1,2})[:.](\\d{2}))?", RegexOption.IGNORE_CASE)

        // Pattern 2: "9:00 Matematik" style
        val pattern2 = Regex("(\\d{1,2})[:.](\\d{2})\\s+([a-züşğıçö]+(?:\\s+[a-züşğıçö]+)*)", RegexOption.IGNORE_CASE)

        text.split("\n").forEach { line ->
            val day = daysOfWeek.find { line.contains(it, ignoreCase = true) } ?: "Pazartesi"

            listOf(pattern1, pattern2).forEach { pattern ->
                pattern.findAll(line).forEach { match ->
                    try {
                        val subject = if (pattern == pattern1) match.groupValues[1] else match.groupValues[3]
                        val hour = if (pattern == pattern1) match.groupValues[2] else match.groupValues[1]
                        val minute = if (pattern == pattern1) match.groupValues[3] else match.groupValues[2]

                        val time = "${hour.padStart(2, '0')}:${minute.padStart(2, '0')}"

                        items.add(ScheduleItem(
                            subject = subject.trim().replaceFirstChar { it.uppercase() },
                            time = time,
                            duration = "60 dk",
                            color = subjectColors[colorIndex % subjectColors.size],
                            day = day,
                            notes = "AI pattern'den çıkarıldı"
                        ))
                        colorIndex++
                    } catch (e: Exception) {
                        // Skip invalid matches
                    }
                }
            }
        }

        return items
    }

    private fun createSmartScheduleFromText(text: String): List<ScheduleItem> {
        val subjects = extractSubjectsWithAI(text)
        val items = mutableListOf<ScheduleItem>()

        var startHour = 9
        var dayIndex = 0

        subjects.forEachIndexed { index, subject ->
            val day = daysOfWeek[dayIndex % daysOfWeek.size]
            val time = String.format("%02d:00", startHour)
            val duration = listOf("60 dk", "90 dk", "120 dk").random()

            items.add(ScheduleItem(
                subject = subject,
                time = time,
                duration = duration,
                color = subjectColors[index % subjectColors.size],
                day = day,
                notes = "AI metinden çıkarıldı"
            ))

            startHour += 2
            if (startHour > 16) {
                startHour = 9
                dayIndex++
            }
        }

        return items
    }

    private fun extractSimpleSubjects(text: String): List<ScheduleItem> {
        val words = text.split(Regex("[\\s.,;:!?\\-()\\[\\]{}\"']+"))
            .map { it.trim() }
            .filter { it.length > 3 }
            .distinct()

        val foundSubjects = words.filter { word ->
            extractSubjects(word).isNotEmpty() ||
            listOf("matematik", "fizik", "kimya", "biyoloji", "türkçe", "ingilizce",
                  "tarih", "coğrafya", "felsefe", "edebiyat", "geometri").any {
                word.contains(it, ignoreCase = true)
            }
        }.take(6)

        return if (foundSubjects.isNotEmpty()) {
            foundSubjects.mapIndexed { index, subject ->
                ScheduleItem(
                    subject = subject.replaceFirstChar { it.uppercase() },
                    time = String.format("%02d:00", 9 + index * 2),
                    duration = listOf("60 dk", "90 dk").random(),
                    color = subjectColors[index % subjectColors.size],
                    day = daysOfWeek[index % daysOfWeek.size],
                    notes = "Basit çıkarım"
                )
            }
        } else emptyList()
    }

    private fun extractSubjectsWithAI(text: String): List<String> {
        // AI tarafından bahsedilen konuları daha akıllıca çıkar
        val subjects = mutableSetOf<String>()
        val lowerText = text.lowercase()

        // Common subject variations
        val subjectMap = mapOf(
            "matematik" to listOf("matematik", "math", "hesap", "sayı", "geometri", "algebra"),
            "fizik" to listOf("fizik", "physics", "kuvvet", "hareket", "enerji"),
            "kimya" to listOf("kimya", "chemistry", "molekül", "atom", "reaksiyon"),
            "biyoloji" to listOf("biyoloji", "biology", "hücre", "bitki", "hayvan", "canlı"),
            "türkçe" to listOf("türkçe", "turkish", "dil", "edebiyat", "şiir", "öykü"),
            "ingilizce" to listOf("ingilizce", "english", "vocabulary", "grammar"),
            "tarih" to listOf("tarih", "history", "geçmiş", "osmanlı", "selçuklu"),
            "coğrafya" to listOf("coğrafya", "geography", "harita", "iklim", "yer"),
            "felsefe" to listOf("felsefe", "philosophy", "düşünce", "mantık"),
            "müzik" to listOf("müzik", "music", "nota", "ses", "enstrüman"),
            "resim" to listOf("resim", "art", "sanat", "boyama", "çizim"),
            "beden" to listOf("beden", "spor", "physical", "hareket", "sağlık")
        )

        subjectMap.forEach { (subject, variations) ->
            if (variations.any { lowerText.contains(it) }) {
                subjects.add(subject.replaceFirstChar { it.uppercase() })
            }
        }

        return subjects.toList().ifEmpty { listOf("Genel Ders", "Çalışma") }
    }

    private fun parseScheduleLine(line: String, day: String, color: Color): ScheduleItem? {
        try {
            // Extract time (HH:MM format)
            val timeRegex = Regex("(\\d{1,2})[:.](\\d{2})")
            val timeMatch = timeRegex.find(line)

            if (timeMatch != null && day.isNotEmpty()) {
                val hour = timeMatch.groupValues[1].padStart(2, '0')
                val minute = timeMatch.groupValues[2]
                val time = "$hour:$minute"

                // Extract subject (everything before time or after time until duration)
                var subject = line.replace(timeRegex, "").trim()

                // Extract duration
                val durationRegex = Regex("(\\d+)\\s*(dk|dakika|saat)")
                val durationMatch = durationRegex.find(line)
                val duration = if (durationMatch != null) {
                    val value = durationMatch.groupValues[1].toInt()
                    val unit = durationMatch.groupValues[2]
                    when (unit) {
                        "saat" -> "${value * 60} dk"
                        else -> "$value dk"
                    }
                } else {
                    "60 dk" // Default duration
                }

                // Clean subject name
                subject = subject.replace(durationRegex, "").trim()
                subject = subject.replace(Regex("[\\-.:,]"), "").trim()
                if (subject.isEmpty()) subject = "Ders"

                return ScheduleItem(
                    subject = subject,
                    time = time,
                    duration = duration,
                    color = color,
                    day = day,
                    notes = ""
                )
            }
        } catch (e: Exception) {
            // Return null if parsing fails
        }
        return null
    }

    private fun parseAlternativeFormat(line: String, day: String, color: Color): ScheduleItem? {
        try {
            // For lines like "Matematik - 09:00 - 90 dk"
            val parts = line.split("-").map { it.trim() }
            if (parts.size >= 2 && day.isNotEmpty()) {
                val subject = parts[0].trim()

                var time = "09:00"
                var duration = "60 dk"

                for (i in 1 until parts.size) {
                    val part = parts[i].trim()
                    when {
                        part.matches(Regex("\\d{1,2}[:.:]\\d{2}")) -> time = part.replace(".", ":")
                        part.contains("dk") || part.contains("dakika") || part.contains("saat") -> {
                            val durationMatch = Regex("(\\d+)\\s*(dk|dakika|saat)").find(part)
                            if (durationMatch != null) {
                                val value = durationMatch.groupValues[1].toInt()
                                val unit = durationMatch.groupValues[2]
                                duration = when (unit) {
                                    "saat" -> "${value * 60} dk"
                                    else -> "$value dk"
                                }
                            }
                        }
                    }
                }

                return ScheduleItem(
                    subject = subject,
                    time = time,
                    duration = duration,
                    color = color,
                    day = day,
                    notes = ""
                )
            }
        } catch (e: Exception) {
            // Return null if parsing fails
        }
        return null
    }

    private fun createBasicScheduleFromText(text: String): List<ScheduleItem> {
        val scheduleItems = mutableListOf<ScheduleItem>()
        val subjects = extractSubjects(text)

        var startHour = 9
        var dayIndex = 0

        subjects.forEachIndexed { index, subject ->
            val day = daysOfWeek[dayIndex % daysOfWeek.size]
            val time = String.format("%02d:00", startHour)
            val duration = "${(60..120).random()} dk"
            val color = subjectColors[index % subjectColors.size]

            scheduleItems.add(
                ScheduleItem(
                    subject = subject,
                    time = time,
                    duration = duration,
                    color = color,
                    day = day,
                    notes = ""
                )
            )

            startHour += 2
            if (startHour > 16) {
                startHour = 9
                dayIndex++
            }
        }

        return scheduleItems
    }

    private fun extractSubjects(text: String): List<String> {
        val commonSubjects = listOf(
            "matematik", "fizik", "kimya", "biyoloji", "türkçe", "edebiyat", "tarih",
            "coğrafya", "felsefe", "psikoloji", "sosyoloji", "economics", "İngilizce",
            "almanca", "fransızca", "müzik", "resim", "beden", "din", "geometri",
            "analiz", "trigonometri", "calculus", "algebra", "istatistik"
        )

        val foundSubjects = mutableSetOf<String>()
        val words = text.lowercase().split(Regex("[\\s,.:;!?\\-]+"))

        for (word in words) {
            commonSubjects.forEach { subject ->
                if (word.contains(subject) || subject.contains(word)) {
                    foundSubjects.add(subject.replaceFirstChar { it.uppercase() })
                }
            }
        }

        // If no subjects found, create some generic ones
        if (foundSubjects.isEmpty()) {
            return listOf("Matematik", "Fen Bilgisi", "Türkçe", "Sosyal Bilgiler")
        }

        return foundSubjects.take(8).toList() // Limit to 8 subjects
    }

    private fun createFallbackSchedule(aiResponse: String): List<ScheduleItem> {
        // Create a basic weekly schedule as fallback
        return listOf(
            ScheduleItem(subject = "Matematik", time = "09:00", duration = "90 dk", color = Color(0xFF2196F3), day = "Pazartesi", notes = "AI Ders Planından"),
            ScheduleItem(subject = "Fen Bilgisi", time = "11:00", duration = "60 dk", color = Color(0xFF4CAF50), day = "Pazartesi", notes = "AI Ders Planından"),
            ScheduleItem(subject = "Türkçe", time = "14:00", duration = "90 dk", color = Color(0xFFF44336), day = "Salı", notes = "AI Ders Planından"),
            ScheduleItem(subject = "Sosyal Bilgiler", time = "10:00", duration = "60 dk", color = Color(0xFF9C27B0), day = "Çarşamba", notes = "AI Ders Planından"),
            ScheduleItem(subject = "İngilizce", time = "15:00", duration = "90 dk", color = Color(0xFFFF9800), day = "Perşembe", notes = "AI Ders Planından"),
            ScheduleItem(subject = "Matematik", time = "09:30", duration = "60 dk", color = Color(0xFF2196F3), day = "Cuma", notes = "AI Ders Planından")
        )
    }
}