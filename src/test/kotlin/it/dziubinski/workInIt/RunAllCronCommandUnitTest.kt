package it.dziubinski.workInIt

import io.kotest.core.spec.style.StringSpec
import io.mockk.*
import it.dziubinski.workInIt.cron.bulldogJob.BulldogJobCron
import it.dziubinski.workInIt.cron.inHireIo.InHireIoCron
import it.dziubinski.workInIt.cron.indeedCom.IndeedComCron
import it.dziubinski.workInIt.cron.justJoinIt.JustJoinItCron
import it.dziubinski.workInIt.cron.noFluffJobsCom.NoFluffJobsComCron
import it.dziubinski.workInIt.cron.pracujPl.PracujPlCron
import it.dziubinski.workInIt.cron.solidJobs.SolidJobsCron

class RunAllCronCommandUnitTest(
) : StringSpec({
    val justJoinItCron = mockk<JustJoinItCron>()
    val noFluffJobsComCron = mockk<NoFluffJobsComCron>()
    val solidJobsCron = mockk<SolidJobsCron>()
    val bulldogJobCron = mockk<BulldogJobCron>()
    val inHireIoCron = mockk<InHireIoCron>()
    val pracujPlCron = mockk<PracujPlCron>()
    val indeedComCron = mockk<IndeedComCron>()
    val runCronCommand = RunCronCommand(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron, inHireIoCron, pracujPlCron, indeedComCron)

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
        every { inHireIoCron.getCronFunctionArray() } answers { callOriginal() }
        every { inHireIoCron.getTotalOffersNumber() } answers {}
        every { inHireIoCron.getKotlinOffersNumber() } answers {}
        every { inHireIoCron.getPhpOffersNumber() } answers {}
        every { pracujPlCron.getCronFunctionArray() } answers { callOriginal() }
        every { pracujPlCron.getTotalOffersNumber() } answers {}
        every { pracujPlCron.getKotlinOffersNumber() } answers {}
        every { pracujPlCron.getPhpOffersNumber() } answers {}
        every { indeedComCron.getCronFunctionArray() } answers { callOriginal() }
        every { indeedComCron.getTotalOffersNumber() } answers {}

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
        verify(exactly = 1) { inHireIoCron.getCronFunctionArray() }
        verify(exactly = 1) { inHireIoCron.getTotalOffersNumber() }
        verify(exactly = 1) { inHireIoCron.getKotlinOffersNumber() }
        verify(exactly = 1) { inHireIoCron.getPhpOffersNumber() }
        verify(exactly = 1) { pracujPlCron.getCronFunctionArray() }
        verify(exactly = 1) { pracujPlCron.getTotalOffersNumber() }
        verify(exactly = 1) { pracujPlCron.getKotlinOffersNumber() }
        verify(exactly = 1) { pracujPlCron.getPhpOffersNumber() }
        verify(exactly = 1) { indeedComCron.getCronFunctionArray() }
        verify(exactly = 1) { indeedComCron.getTotalOffersNumber() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron, inHireIoCron, pracujPlCron, indeedComCron)
    }

    "should run one job portal cron" {
        every { justJoinItCron.getCronFunctionArray() } answers { callOriginal() }
        every { justJoinItCron.getTotalOffersNumber() } answers {}
        every { justJoinItCron.getKotlinOffersNumber() } answers {}
        every { justJoinItCron.getPhpOffersNumber() } answers {}

        runCronCommand.executeSingleCron("JUST_JOIN_IT", true)

        verify(exactly = 1) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 1) { justJoinItCron.getTotalOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getKotlinOffersNumber() }
        verify(exactly = 1) { justJoinItCron.getPhpOffersNumber() }
        verify(exactly = 0) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 0) { solidJobsCron.getCronFunctionArray() }
        verify(exactly = 0) { bulldogJobCron.getCronFunctionArray() }
        verify(exactly = 0) { inHireIoCron.getCronFunctionArray() }
        verify(exactly = 0) { pracujPlCron.getCronFunctionArray() }
        verify(exactly = 0) { indeedComCron.getCronFunctionArray() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron, inHireIoCron)
    }
    "should not run one job portal cron because wrong cronName" {
        runCronCommand.executeSingleCron("wornCronName", true)

        verify(exactly = 0) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 0) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 0) { solidJobsCron.getCronFunctionArray() }
        verify(exactly = 0) { bulldogJobCron.getCronFunctionArray() }
        verify(exactly = 0) { inHireIoCron.getCronFunctionArray() }
        verify(exactly = 0) { pracujPlCron.getCronFunctionArray() }
        verify(exactly = 0) { indeedComCron.getCronFunctionArray() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron, inHireIoCron)
    }
})