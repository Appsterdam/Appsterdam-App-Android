package rs.appsterdam.app.presentation.about

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import rs.appsterdam.app.databinding.FragmentMemberDetailsBinding

@AndroidEntryPoint
class MemberDetailsFragment : DialogFragment() {

    private val args: MemberDetailsFragmentArgs by navArgs()

    private val binding: FragmentMemberDetailsBinding by lazy {
        FragmentMemberDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding.item = args.member
        binding.twitter.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.twitter.com/${args.member.twitter}")
                )
            )
        }
        binding.linkedin.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.linkedin.com/in/${args.member.linkedin}")
                )
            )
        }
        binding.website.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.member.website)))
        }
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(args.member.name)
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            dismiss()
        }
        return builder.setView(binding.root).create()
    }
}
