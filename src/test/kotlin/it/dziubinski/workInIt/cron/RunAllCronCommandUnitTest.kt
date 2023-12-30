package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import it.dziubinski.workInIt.RunAllCronCommand

class RunAllCronCommandUnitTest(
) : StringSpec({
    val justJoinItCron = mockk<JustJoinItCron>()
    val noFluffJobsComCron = mockk<NoFluffJobsComCron>()
    val runAllCronCommand = RunAllCronCommand(justJoinItCron, noFluffJobsComCron)

    "should run all cron methods" {
        every { justJoinItCron.getCronFunctionArray() } answers { callOriginal() }
        every { justJoinItCron.getTotalOffersNumber() } answers {}
        every { justJoinItCron.getKotlinOffersNumber() } answers {}
        every { justJoinItCron.getPhpOffersNumber() } answers {}
        every { noFluffJobsComCron.getCronFunctionArray() } answers { callOriginal() }
        every { noFluffJobsComCron.getTotalOffersNumber() } answers {}
        every { noFluffJobsComCron.getKotlinOffersNumber() } answers {}
        every { noFluffJobsComCron.getPhpOffersNumber() } answers {}

        runAllCronCommand.executeCommand()

        verify(exactly = 1) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 1) { justJoinItCron.getTotalOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getKotlinOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getPhpOffersNumber() }
        verify(exactly = 1) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 1) { noFluffJobsComCron.getTotalOffersNumber() }
        verify(exactly = 1) { noFluffJobsComCron.getKotlinOffersNumber() }
        verify(exactly = 1) { noFluffJobsComCron.getPhpOffersNumber() }

        confirmVerified(justJoinItCron, noFluffJobsComCron)
    }
})