package com.emirhankarci.tutorly.domain.entity

data class StudySession(
    val grade: Int,
    val subject: String,
    val chapter: ChapterInfo
) {
    fun getDisplayTitle(): String {
        return "${grade}. Sınıf $subject - ${chapter.title}"
    }

    fun getShortTitle(): String {
        return "${grade}. Sınıf $subject"
    }
}

object StudySessionHelper {
    fun getChaptersForGradeAndSubject(grade: Int, subject: String): List<ChapterInfo> {
        val key = GradeSubjectKey(grade, subject)
        return chaptersByGradeAndSubject[key] ?: emptyList()
    }

    fun createStudySession(grade: Int, subject: String, chapterTitle: String): StudySession? {
        val chapters = getChaptersForGradeAndSubject(grade, subject)
        val chapter = chapters.find { it.title == chapterTitle }
        return if (chapter != null) {
            StudySession(grade, subject, chapter)
        } else {
            null
        }
    }

    fun getAvailableGrades(): List<Int> {
        return chaptersByGradeAndSubject.keys.map { it.grade }.distinct().sorted()
    }

    fun getAvailableSubjects(): List<String> {
        return chaptersByGradeAndSubject.keys.map { it.subject }.distinct()
    }

    fun getSubjectsForGrade(grade: Int): List<String> {
        return chaptersByGradeAndSubject.keys
            .filter { it.grade == grade }
            .map { it.subject }
            .distinct()
    }
}