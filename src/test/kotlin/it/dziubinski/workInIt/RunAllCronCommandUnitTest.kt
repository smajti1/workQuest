package it.dziubinski.workInIt

import io.kotest.core.spec.style.StringSpec
import io.mockk.*
import it.dziubinski.workInIt.cron.bulldogJob.BulldogJobCron
import it.dziubinski.workInIt.cron.inHireIo.InHireIoCron
import it.dziubinski.workInIt.cron.indeedCom.IndeedComCron
import it.dziubinski.workInIt.cron.itLeaders.ItLeadersCron
import it.dziubinski.workInIt.cron.justJoinIt.JustJoinItCron
import it.dziubinski.workInIt.cron.noFluffJobsCom.NoFluffJobsComCron
import it.dziubinski.workInIt.cron.pracujPl.PracujPlCron
import it.dziubinski.workInIt.cron.solidJobs.SolidJobsCron
import it.dziubinski.workInIt.cron.startupJobs.StartupJobsCron
import it.dziubinski.workInIt.cron.theProtocol.TheProtocolCron

class RunAllCronCommandUnitTest : StringSpec({
    val justJoinItCron = mockk<JustJoinItCron>()
    val noFluffJobsComCron = mockk<NoFluffJobsComCron>()
    val solidJobsCron = mockk<SolidJobsCron>()
    val bulldogJobCron = mockk<BulldogJobCron>()
    val inHireIoCron = mockk<InHireIoCron>()
    val pracujPlCron = mockk<PracujPlCron>()
    val indeedComCron = mockk<IndeedComCron>()
    val theProtocolCron = mockk<TheProtocolCron>()
    val startupJobsCron = mockk<StartupJobsCron>()
    val itLeadersCron = mockk<ItLeadersCron>()
    val runCronCommand =
        RunCronCommand(
            justJoinItCron,
            noFluffJobsComCron,
            solidJobsCron,
            bulldogJobCron,
            inHireIoCron,
            pracujPlCron,
            indeedComCron,
            theProtocolCron,
            startupJobsCron,
            itLeadersCron,
        )

    beforeEach {
        clearAllMocks()
    }

    "should run all cron methods" {
        every { justJoinItCron.getCronFunctionArray() } answers { callOriginal() }
        every { justJoinItCron.getOffersNumber(0L) } answers { }
        every { noFluffJobsComCron.getCronFunctionArray() } answers { callOriginal() }
        every { noFluffJobsComCron.getOffersNumber(0L) } answers { }
        every { solidJobsCron.getCronFunctionArray() } answers { callOriginal() }
        every { solidJobsCron.getOffersNumber(0L) } answers { }
        every { bulldogJobCron.getCronFunctionArray() } answers { callOriginal() }
        every { bulldogJobCron.getOffersNumber(0L) } answers { }
        every { inHireIoCron.getCronFunctionArray() } answers { callOriginal() }
        every { inHireIoCron.getOffersNumber(0L) } answers { }
        every { pracujPlCron.getCronFunctionArray() } answers { callOriginal() }
        every { pracujPlCron.getOffersNumber(0L) } answers { }
        every { indeedComCron.getCronFunctionArray() } answers { callOriginal() }
        every { indeedComCron.getOffersNumber(0L) } answers { }
        every { theProtocolCron.getCronFunctionArray() } answers { callOriginal() }
        every { theProtocolCron.getOffersNumber(0L) } answers { }
        every { startupJobsCron.getCronFunctionArray() } answers { callOriginal() }
        every { startupJobsCron.getOffersNumber(0L) } answers { }
        every { itLeadersCron.getCronFunctionArray() } answers { callOriginal() }
        every { itLeadersCron.getOffersNumber(0L) } answers { }

        runCronCommand.executeRunAllCron(true)

        verify(exactly = 1) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 1) { justJoinItCron.getOffersNumber(0L) }
        verify(exactly = 1) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 1) { noFluffJobsComCron.getOffersNumber(0L) }
        verify(exactly = 1) { solidJobsCron.getCronFunctionArray() }
        verify(exactly = 1) { solidJobsCron.getOffersNumber(0L) }
        verify(exactly = 1) { bulldogJobCron.getCronFunctionArray() }
        verify(exactly = 1) { bulldogJobCron.getOffersNumber(0L) }
        verify(exactly = 1) { inHireIoCron.getCronFunctionArray() }
        verify(exactly = 1) { inHireIoCron.getOffersNumber(0L) }
        verify(exactly = 1) { pracujPlCron.getCronFunctionArray() }
        verify(exactly = 1) { pracujPlCron.getOffersNumber(0L) }
        verify(exactly = 1) { indeedComCron.getCronFunctionArray() }
        verify(exactly = 1) { indeedComCron.getOffersNumber(0L) }
        verify(exactly = 1) { theProtocolCron.getCronFunctionArray() }
        verify(exactly = 1) { theProtocolCron.getOffersNumber(0L) }
        verify(exactly = 1) { startupJobsCron.getCronFunctionArray() }
        verify(exactly = 1) { startupJobsCron.getOffersNumber(0L) }
        verify(exactly = 1) { itLeadersCron.getCronFunctionArray() }
        verify(exactly = 1) { itLeadersCron.getOffersNumber(0L) }

        confirmVerified(
            justJoinItCron,
            noFluffJobsComCron,
            solidJobsCron,
            bulldogJobCron,
            inHireIoCron,
            pracujPlCron,
            indeedComCron,
            theProtocolCron,
            startupJobsCron,
            itLeadersCron,
        )
    }

    "should run one job portal cron" {
        every { justJoinItCron.getCronFunctionArray() } answers { callOriginal() }
        every { justJoinItCron.getOffersNumber(0L) } answers {}

        runCronCommand.executeSingleCron("JUST_JOIN_IT", true)

        verify(exactly = 1) { justJoinItCron.getCronFunctionArray() }
        verify(exactly = 1) { justJoinItCron.getOffersNumber(0L) }
        verify(exactly = 0) { noFluffJobsComCron.getCronFunctionArray() }
        verify(exactly = 0) { solidJobsCron.getCronFunctionArray() }
        verify(exactly = 0) { bulldogJobCron.getCronFunctionArray() }
        verify(exactly = 0) { inHireIoCron.getCronFunctionArray() }
        verify(exactly = 0) { pracujPlCron.getCronFunctionArray() }
        verify(exactly = 0) { indeedComCron.getCronFunctionArray() }
        verify(exactly = 0) { theProtocolCron.getCronFunctionArray() }
        verify(exactly = 0) { startupJobsCron.getCronFunctionArray() }
        verify(exactly = 0) { itLeadersCron.getCronFunctionArray() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron, inHireIoCron, startupJobsCron, itLeadersCron)
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
        verify(exactly = 0) { theProtocolCron.getCronFunctionArray() }
        verify(exactly = 0) { startupJobsCron.getCronFunctionArray() }
        verify(exactly = 0) { itLeadersCron.getCronFunctionArray() }

        confirmVerified(justJoinItCron, noFluffJobsComCron, solidJobsCron, bulldogJobCron, inHireIoCron, startupJobsCron, itLeadersCron)
    }
})