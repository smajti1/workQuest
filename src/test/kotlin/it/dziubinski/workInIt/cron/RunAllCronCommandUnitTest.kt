package it.dziubinski.workInIt.cron

import io.kotest.core.spec.style.StringSpec
import io.mockk.*
import it.dziubinski.workInIt.RunCronCommand

class RunAllCronCommandUnitTest(
) : StringSpec({
    val justJoinItCron = mockk<JustJoinItCron>()
    val noFluffJobsComCron = mockk<NoFluffJobsComCron>()
    val solidJobsCron = mockk<SolidJobsCron>()
    val bulldogJobCron = mockk<BulldogJobCron>()
    val runCronCommand = RunCronCommand(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron)

    beforeEach {
        clearAllMocks()
    }

    "should run all cron methods" {
        every { justJoinItCron.getCronFunctionArray() } answers { callOriginal() }
        every { justJoinItCron.getTotalOffersNumber() } answers {}
        every { justJoinItCron.getKotlinOffersNumber() } answers {}
        every { justJoinItCron.getPhpOffersNumber() } answers {}
        every { noFluffJobsComCron.getCronFunctionArray() } answers { callOriginal() }
        every { noFluffJobsComCron.getTotalOffersNumber() } answers {}
        every { noFluffJobsComCron.getKotlinOffersNumber() } answers {}
        every { noFluffJobsComCron.getPhpOffersNumber() } answers {}
        every { solidJobsCron.getCronFunctionArray() } answers { callOriginal() }
        every { solidJobsCron.getOffersNumber() } answers {}
        every { bulldogJobCron.getCronFunctionArray() } answers { callOriginal() }
        every { bulldogJobCron.getTotalOffersNumber() } answers {}
        every { bulldogJobCron.getKotlinOffersNumber() } answers {}
        every { bulldogJobCron.getPhpOffersNumber() } answers {}

        runCronCommand.executeRunAllCron(true)

        verify(exactly = 1) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 1) { justJoinItCron.getTotalOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getKotlinOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getPhpOffersNumber() }
        verify(exactly = 1) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 1) { noFluffJobsComCron.getTotalOffersNumber() }
        verify(exactly = 1) { noFluffJobsComCron.getKotlinOffersNumber() }
        verify(exactly = 1) { noFluffJobsComCron.getPhpOffersNumber() }
        verify(exactly = 1) { solidJobsCron.getCronFunctionArray() }
        verify(exactly = 1) { solidJobsCron.getOffersNumber() }
        verify(exactly = 1) { bulldogJobCron.getCronFunctionArray() }
        verify(exactly = 1) { bulldogJobCron.getTotalOffersNumber() }
        verify(exactly = 1) { bulldogJobCron.getKotlinOffersNumber() }
        verify(exactly = 1) { bulldogJobCron.getPhpOffersNumber() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron)
    }

    "should run one job portal cron" {
        every { justJoinItCron.getCronFunctionArray() } answers { callOriginal() }
        every { justJoinItCron.getTotalOffersNumber() } answers {}
        every { justJoinItCron.getKotlinOffersNumber() } answers {}
        every { justJoinItCron.getPhpOffersNumber() } answers {}

        runCronCommand.executeSingleCron("justJoinItCron", true)

        verify(exactly = 1) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 1) { justJoinItCron.getTotalOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getKotlinOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getPhpOffersNumber() }
        verify(exactly = 0) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 0) { solidJobsCron.getCronFunctionArray() }
        verify(exactly = 0) { bulldogJobCron.getCronFunctionArray() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron)
    }
    "should not run one job portal cron because wrong cronName" {
        runCronCommand.executeSingleCron("wornCronName", true)

        verify(exactly = 0) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 0) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 0) { solidJobsCron.getCronFunctionArray() }
        verify(exactly = 0) { bulldogJobCron.getCronFunctionArray() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron)
    }
})